package com.platon.statistic.service;

import com.alibaba.fastjson.JSON;
import com.platon.statistic.config.BlockChainConfig;
import com.platon.statistic.excption.CandidateException;
import com.platon.statistic.util.PlatOnClient;
import com.platon.statistic.util.SpecialContractApi;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.Node;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Chendongming
 * @Date: 2019/9/6 16:21
 * @Description: 候选人、共识周期验证人、结算周期验证人服务类
 */
@Service
public class CandidateService {
    private static Logger logger = LoggerFactory.getLogger(CandidateService.class);
    @Autowired
    private BlockChainConfig chainConfig;
    @Autowired
    private PlatOnClient client;
    @Autowired
    private SpecialContractApi sca;

    private static final String ERR_MSG="睡眠被中断";

    @Data
    public static class CandidateResult{
        private List<Node> cur;
    }

    /**
     * 从指定区块号初始化BlockChain的结算周期验证人
     * @param blockNumber
     */
    public List<Node> getVerifiers(BigInteger blockNumber) throws InterruptedException, CandidateException {
        List<Node> nodes = new ArrayList<>();
        // ==================================更新当前周期验证人列表=======================================
        while (true) try {
            nodes = sca.getHistoryVerifierList(client.getWeb3j(), blockNumber);
            String msg = JSON.toJSONString(nodes, true);
            logger.debug("当前结算周期验证人(块:{}):{}", blockNumber, msg);
            break;
        } catch (Exception e) {
            // 如果取不到节点列表，证明agent已经追上链，则使用实时接口查询节点列表
            try {
                nodes = getCurVerifiers();
                String msg = JSON.toJSONString(nodes, true);
                logger.debug("当前结算周期验证人(实时):{}", msg);
                break;
            } catch (Exception e1) {
                logger.error("【查询当前结算验证人-底层出错】查询实时结算周期验证人出错,将重试:{}", e1.getMessage());
                TimeUnit.SECONDS.sleep(1L);
            }
        }

        if(nodes.isEmpty()){
            throw new CandidateException("查询不到结算周期验证人(当前块号="+blockNumber+")");
        }
        return nodes;
    }

    /**
     * 从指定区块号初始化BlockChain的共识周期验证人
     * @param blockNumber
     */
    public List<Node> getValidators(BigInteger blockNumber) throws InterruptedException, CandidateException {
        // ==================================更新当前周期验证人列表=======================================
        List<Node> nodes = new ArrayList<>();
        while (true) try {
            nodes = sca.getHistoryValidatorList(client.getWeb3j(), blockNumber);
            String msg = JSON.toJSONString(nodes, true);
            logger.debug("当前共识周期验证人(始块:{}):{}", blockNumber, msg);
            break;
        } catch (Exception e) {
            // 如果取不到节点列表，证明agent已经追上链，则使用实时接口查询节点列表
            try {
                nodes = getCurValidators();
                String msg = JSON.toJSONString(nodes, true);
                logger.debug("当前共识周期验证人(实时):{}", msg);
                break;
            } catch (Exception e1) {
                logger.error("【查询当前共识验证人-底层出错】查询实时共识周期验证人出错,将重试:", e1);
                TimeUnit.SECONDS.sleep(1L);
            }
        }

        if(nodes.isEmpty()){
            throw new CandidateException("查询不到共识周期验证人(当前块号="+blockNumber+")");
        }
        return nodes;
    }

    /**
     * 获取实时候选人列表
     * @return
     * @throws Exception
     */
    public List<Node> getCurCandidates() throws InterruptedException {
        while (true) try {
            BaseResponse<List<Node>> br = client.getNodeContract().getCandidateList().send();
            if (!br.isStatusOk()) {
                throw new CandidateException(br.errMsg);
            }
            return br.data;
        } catch (Exception e) {
            logger.error("底层链查询候选验证节点列表出错,将重试:", e);
            TimeUnit.SECONDS.sleep(1L);
        }
    }

    /**
     * 获取实时结算周期验证人列表
     * @return
     * @throws Exception
     */
    public List<Node> getCurVerifiers() throws InterruptedException {
        while (true) try {
            BaseResponse<List<Node>> br = client.getNodeContract().getVerifierList().send();
            if (!br.isStatusOk()) {
                throw new CandidateException(br.errMsg);
            }
            return br.data;
        } catch (Exception e) {
            logger.error("底层链查询实时结算周期验证节点列表出错,将重试:{}", e.getMessage());
            TimeUnit.SECONDS.sleep(1L);
        }
    }

    /**
     * 获取实时共识周期验证人列表
     * @return
     * @throws Exception
     */
    public List<Node> getCurValidators() throws InterruptedException {
        while (true) try {
            BaseResponse<List<Node>> br = client.getNodeContract().getValidatorList().send();
            if (!br.isStatusOk()) {
                throw new CandidateException(br.errMsg);
            }
            return br.data;
        } catch (Exception e) {
            logger.error("底层链查询实时共识周期验证节点列表出错,将重试:", e);
            TimeUnit.SECONDS.sleep(1L);
        }
    }
}
