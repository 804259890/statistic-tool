package com.platon.statistic.service;

import com.platon.statistic.arg.Params;
import com.platon.statistic.bean.PrepareQC;
import com.platon.statistic.bean.StatisticNode;
import com.platon.statistic.cache.NodeCache;
import com.platon.statistic.config.BlockChainConfig;
import com.platon.statistic.enums.PeriodEnum;
import com.platon.statistic.excption.CandidateException;
import com.platon.statistic.excption.NoSuchBeanException;
import com.platon.statistic.util.HexTool;
import com.platon.statistic.util.PlatOnClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.platon.bean.Node;
import org.web3j.protocol.core.methods.response.PlatonBlock;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Slf4j
@Service
public class StatisticService {

    // 当前结算周期轮数
    private BigInteger curSettleEpoch;
    // 当前共识周期轮数
    private BigInteger curConsensusEpoch;
    // 当前结算周期验证人
    private List<Node> curVerifier = new ArrayList<>();
    // 当前共识周期验证人
    private List<Node> curValidator = new ArrayList <>();

    private PlatonBlock.Block curBlock;

    @Autowired
    private PlatOnClient client;
    @Autowired
    private BlockChainConfig chainConfig;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private NodeCache nodeCache;
    @Autowired
    private DbService dbService;

    private boolean needSave = false;
    private boolean needStop = false;
    private boolean needInit = false;
    public void setNeedInit(boolean needInit){
        this.needInit = needInit;
    }


    private Params param;
    public void init(Params param) {
        this.param = param;
    }

    public void analyze(PlatonBlock.Block block) throws CandidateException, InterruptedException, NoSuchBeanException {
        this.curBlock = block;
        epochChange(curBlock.getNumber().longValue());
        updateNode();

        long endTimestamp = 0;
        if(param.getEndTime()!=null) endTimestamp=param.getEndTime().getTime();
        if(param.getTimestamp()!=null&&param.getTimestamp()>endTimestamp) endTimestamp=param.getTimestamp();
        if(endTimestamp>0&&endTimestamp<=curBlock.getTimestamp().longValue()){
            needSave = true;
            needStop = true;
            // 设置区块服务的停机标识
            log.warn("检测到区块:{}的时间戳:{}大于等于命令行指定的截止时间:{}，程序将结束!",curBlock.getNumber(),curBlock.getTimestamp().longValue(),endTimestamp);
        }
        if(needSave) dbService.insertOrUpdate();
        needSave = false;
        if(needStop) System.exit(1);

    }

    /**
     * 周期变更通知：
     */
    public void epochChange(Long blockNumber) throws InterruptedException, CandidateException {

        this.curConsensusEpoch = BigInteger.valueOf(BigDecimal.valueOf(blockNumber)
                .divide(BigDecimal.valueOf(chainConfig.getConsensusPeriodBlockCount().longValue()), 0, RoundingMode.CEILING).longValue());
        this.curSettleEpoch = BigInteger.valueOf(BigDecimal.valueOf(blockNumber)
                .divide(BigDecimal.valueOf(chainConfig.getSettlePeriodBlockCount().longValue()), 0, RoundingMode.CEILING).longValue());

        boolean isConsensusSwitch = (blockNumber % chainConfig.getConsensusPeriodBlockCount().longValue() == 0);
        if (blockNumber==1||isConsensusSwitch||needInit) {
            log.debug("共识周期切换：Block Number({})", blockNumber);

            curValidator = candidateService.getValidators(blockNumber==1?BigInteger.ZERO:BigInteger.valueOf(blockNumber));

            // 累加节点进入共识周期的次数(只有在共识周期结束时才算一次)
            curValidator.forEach(node -> {
                String nodeId = HexTool.prefix(node.getNodeId());
                StatisticNode sn;
                try {
                    sn = nodeCache.getNode(nodeId);
                }catch (NoSuchBeanException e){
                    sn = new StatisticNode();
                    sn.setNodeId(nodeId);
                    nodeCache.addNode(sn);
                }
                // 区块为1时，节点所参与过的共识周期次数为0，只有共识周期结束后才算一次
                sn.setStatValidatorTime(blockNumber==1?0:sn.getStatValidatorTime()+1);
            });

            if(blockNumber>1&&param.getPeriods()!=null&&param.getPeriods().contains(PeriodEnum.C)&&isConsensusSwitch) needSave = true;
        }

        boolean isSettleSwitch = (blockNumber % chainConfig.getSettlePeriodBlockCount().longValue() == 0);
        if (blockNumber==1||isSettleSwitch||needInit) {
            log.debug("结算周期切换：Block Number({})", blockNumber);
            curVerifier = candidateService.getVerifiers(blockNumber==1?BigInteger.ZERO:BigInteger.valueOf(blockNumber));
            if(blockNumber>1&&param.getPeriods()!=null&&param.getPeriods().contains(PeriodEnum.S)&&isConsensusSwitch) needSave = true;
        }
        needInit=false;
    }


    /**
     * 更新节点信息
     */
    public void updateNode() throws InterruptedException, CandidateException {
        PrepareQC prepareQC = client.getPrepareQC(curBlock.getNumber());
        String signBits = prepareQC.getValidatorSet();

        if(signBits.length()!=curValidator.size()){
            throw new CandidateException("签名节点数:"+signBits+",实际验证人数:"+curValidator.size());
        }

        for (int i=0;i<signBits.length();i++){
            Node node = curValidator.get(i);
            if(node!=null){
                String nodeId = HexTool.prefix(node.getNodeId());
                StatisticNode sn;
                try {
                    sn = nodeCache.getNode(nodeId);
                }catch (NoSuchBeanException e){
                    sn = new StatisticNode();
                    sn.setNodeId(nodeId);
                    nodeCache.addNode(sn);
                }
                sn.setBlockNumber(curBlock.getNumber().longValue());
                sn.setBlockTimestamp(curBlock.getTimestamp().longValue());
                sn.setSettleEpoch(curSettleEpoch.intValue());
                sn.setConsensusEpoch(curConsensusEpoch.intValue());
                char sign = signBits.charAt(i);
                if('x'==sign) sn.setSignCount(sn.getSignCount()+1);
                sn.setUpdateTime(new Date());
            }else {
                log.error("标志{},位[{}-{}]没有对应的验证节点!",signBits,i,signBits.charAt(i));
            }
        }

    }
}
