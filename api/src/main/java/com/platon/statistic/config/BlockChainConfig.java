package com.platon.statistic.config;

import com.alibaba.fastjson.JSON;
import com.platon.statistic.util.PlatOnClient;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.web3j.platon.bean.EconomicConfig;
import org.web3j.utils.Convert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

/**
 * 链参数统一配置项
 * @Auther: Chendongming
 * @Date: 2019/8/10 16:12
 * @Description:
 */
@Data
@Configuration
public class BlockChainConfig {
    private static Logger logger = LoggerFactory.getLogger(BlockChainConfig.class);

    @Autowired
    private PlatOnClient client;

    /*******************以下参数通过rpc接口debug_economicConfig获取*******************/
    //【通用】每个验证人每个共识周期出块数量目标值
    private BigInteger expectBlockCount;
    //【通用】每个共识轮验证节点数量
    private BigInteger consensusValidatorCount;
    //【通用】每个增发周期的分钟数
    private BigInteger additionalCycleMinutes;
    //【通用】每个增发周期内的结算周期数
    private BigInteger settlePeriodCountPerIssue;
    //【通用】出块间隔 = 系统分配的节点出块时间窗口/每个验证人每个view出块数量目标值
    private BigInteger blockInterval;
    //【通用】共识轮区块数 = expectBlockCount x consensusValidatorCount
    private BigInteger consensusPeriodBlockCount;
    //【通用】每个结算周期区块总数=ROUND_DOWN(结算周期规定的分钟数x60/(出块间隔x共识轮区块数))x共识轮区块数
    private BigInteger settlePeriodBlockCount;
    //【通用】每个增发周期区块总数=ROUND_DOWN(增发周期的时间x60/(出块间隔x结算周期区块数))x结算周期区块数
    private BigInteger addIssuePeriodBlockCount;
    //【通用】PlatOn基金会账户地址
    private String platOnFundAccount;
    //【通用】PlatOn基金会账户初始余额
    private BigDecimal platOnFundInitAmount;
    //【通用】开发者激励基金账户地址
    private String communityFundAccount;
    //【通用】开发者激励基金账户初始余额
    private BigDecimal communityFundInitAmount;

    //【质押】质押门槛: 创建验证人最低的质押Token数(LAT)
    private BigDecimal stakeThreshold;
    //【质押】委托门槛(LAT)
    private BigDecimal delegateThreshold;
    //【质押】节点质押退回锁定的结算周期数
    private BigInteger unStakeRefundSettlePeriodCount;

    //【惩罚】违规-低出块率-触发处罚的出块阈值(块数)
    private BigDecimal slashBlockThreshold;
    //【惩罚】低出块率处罚多少个区块奖励
    private BigDecimal slashBlockCount;
    //【惩罚】双签处罚百分比
    private BigDecimal duplicateSignSlashRate;
    //【惩罚】双签奖励百分比
    private BigDecimal duplicateSignReportRate;

    //【治理】文本提案参与率: >
    private BigDecimal minProposalTextParticipationRate;
    //【治理】文本提案支持率：>=
    private BigDecimal minProposalTextSupportRate;
    //【治理】取消提案参与率: >
    private BigDecimal minProposalCancelParticipationRate;
    //【治理】取消提案支持率：>=
    private BigDecimal minProposalCancelSupportRate;
    //【治理】升级提案通过率
    private BigDecimal minProposalUpgradePassRate;
    //【治理】文本提案默认结束轮数
    private BigDecimal proposalTextConsensusRounds;
    //【治理】设置预升级开始轮数
    private BigDecimal versionProposalActiveConsensusRounds;

    //【奖励】激励池分配给出块激励的比例
    private BigDecimal blockRewardRate;
    //【奖励】激励池分配给质押激励的比例
    private BigDecimal stakeRewardRate;

    @PostConstruct
    private void init() throws InterruptedException {
        EconomicConfig dec;
        while (true) try {
            dec = client.getWeb3j().getEconomicConfig().send().getEconomicConfig();
            break;
        } catch (IOException e) {
            logger.error("初始化链配置错误,将重试:{}", e.getMessage());
            TimeUnit.SECONDS.sleep(1);
        }
        String msg = JSON.toJSONString(dec,true);
        logger.info("链上配置:{}",msg);
        updateWithEconomicConfig(dec);
    }

    private void updateWithEconomicConfig(EconomicConfig dec) {
        //【通用】每个验证人每个共识周期出块数量目标值
        this.expectBlockCount=dec.getCommon().getPerRoundBlocks();
        //【通用】每个共识轮验证节点数量
        this.consensusValidatorCount=dec.getCommon().getValidatorCount();
        //【通用】增发周期规定的分钟数
        this.additionalCycleMinutes=dec.getCommon().getAdditionalCycleTime();
        //【通用】出块间隔 = 系统分配的节点出块时间窗口/每个验证人每个view出块数量目标值
        this.blockInterval=dec.getCommon().getNodeBlockTimeWindow().divide(this.expectBlockCount);
        //【通用】共识轮区块数 = expectBlockCount x consensusValidatorCount
        this.consensusPeriodBlockCount=this.expectBlockCount.multiply(dec.getCommon().getValidatorCount());
        //【通用】每个结算周期区块总数=ROUND_DOWN(结算周期规定的分钟数x60/(出块间隔x共识轮区块数))x共识轮区块数
        this.settlePeriodBlockCount=dec.getCommon().getExpectedMinutes()
                .multiply(BigInteger.valueOf(60))
                .divide(this.blockInterval.multiply(this.consensusPeriodBlockCount))
                .multiply(this.consensusPeriodBlockCount);
        //【通用】每个增发周期区块总数=ROUND_DOWN(增发周期的时间x60/(出块间隔x结算周期区块数))x结算周期区块数
        this.addIssuePeriodBlockCount=this.additionalCycleMinutes
                .multiply(BigInteger.valueOf(60))
                .divide(this.blockInterval.multiply(this.settlePeriodBlockCount))
                .multiply(this.settlePeriodBlockCount);
        //【通用】每个增发周期内的结算周期数=增发周期区块数/结算周期区块数
        this.settlePeriodCountPerIssue=this.addIssuePeriodBlockCount.divide(this.settlePeriodBlockCount);
        //【通用】PlatOn基金会账户地址
        this.platOnFundAccount=dec.getInnerAcc().getPlatONFundAccount();
        //【通用】PlatOn基金会账户初始余额
        this.platOnFundInitAmount=dec.getInnerAcc().getPlatONFundBalance();
        //【通用】社区开发者激励基金账户地址
        this.communityFundAccount=dec.getInnerAcc().getCDFAccount();
        //【通用】社区开发者激励基金账户初始余额
        this.communityFundInitAmount=dec.getInnerAcc().getCDFBalance();

        //【质押】创建验证人最低的质押Token数(LAT)
        this.stakeThreshold= Convert.fromVon(dec.getStaking().getStakeThreshold(), Convert.Unit.LAT);
        //【质押】委托人每次委托及赎回的最低Token数(LAT)
        this.delegateThreshold=Convert.fromVon(dec.getStaking().getMinimumThreshold(), Convert.Unit.LAT);
        //【质押】节点质押退回锁定的结算周期数
        this.unStakeRefundSettlePeriodCount=dec.getStaking().getUnStakeFreezeRatio();
        //【惩罚】违规-低出块率-触发处罚的出块率阈值 60%
        this.slashBlockThreshold=dec.getSlashing().getPackAmountAbnormal();
        //【惩罚】低出块率处罚多少个区块奖励
        this.slashBlockCount=dec.getSlashing().getNumberOfBlockRewardForSlashing();
        //【惩罚】双签处罚百分比
        this.duplicateSignSlashRate=dec.getSlashing().getDuplicateSignHighSlashing();
        //【惩罚】双签奖励百分比
        this.duplicateSignReportRate=dec.getSlashing().getDuplicateSignReportReward().divide(BigDecimal.valueOf(100),2,RoundingMode.FLOOR);
        //【惩罚】双签处罚百分比
        this.duplicateSignSlashRate=dec.getSlashing().getDuplicateSignHighSlashing().divide(BigDecimal.valueOf(100),2, RoundingMode.FLOOR);
        //【治理】文本提案参与率: >
        this.minProposalTextParticipationRate=dec.getGov().getTextProposalVoteRate();
        //【治理】文本提案支持率：>=
        this.minProposalTextSupportRate=dec.getGov().getTextProposalSupportRate();
        //【治理】取消提案参与率: >
        this.minProposalCancelParticipationRate=dec.getGov().getCancelProposalVoteRate();
        //【治理】取消提案支持率：>=
        this.minProposalCancelSupportRate=dec.getGov().getCancelProposalSupportRate();
        //【治理】升级提案通过率
        this.minProposalUpgradePassRate=dec.getGov().getVersionProposalSupportRate();
        //【治理】文本提案投票周期
        this.proposalTextConsensusRounds=dec.getGov().getTextProposalVoteDurationSeconds()
                .divide(new BigDecimal(this.blockInterval.multiply(dec.getCommon().getPerRoundBlocks()).multiply(dec.getCommon().getValidatorCount())),0,RoundingMode.FLOOR);
        //【治理】设置预升级开始轮数
        this.versionProposalActiveConsensusRounds=dec.getGov().getVersionProposalVoteDurationSeconds()
                .divide(new BigDecimal(this.blockInterval.multiply(dec.getCommon().getPerRoundBlocks()).multiply(dec.getCommon().getValidatorCount())),0,RoundingMode.FLOOR);
        //【奖励】激励池分配给出块激励的比例
        this.blockRewardRate=dec.getReward().getNewBlockRate().divide(BigDecimal.valueOf(100),2,RoundingMode.FLOOR);
        //【奖励】激励池分配给质押激励的比例 = 1-区块奖励比例
        this.stakeRewardRate=BigDecimal.ONE.subtract(this.blockRewardRate);
    }
}