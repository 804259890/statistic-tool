package com.platon.statistic.util;

import com.alibaba.fastjson.JSON;
import com.platon.statistic.bean.PrepareQC;
import com.platon.statistic.bean.RpcParam;
import com.platon.statistic.bean.Web3Response;
import com.platon.statistic.excption.ContractInvokeException;
import com.platon.statistic.excption.HttpRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.platon.contracts.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: dongqile
 * Date: 2018/10/25
 * Time: 14:42
 */
@Component
public class PlatOnClient {
    private static Logger logger = LoggerFactory.getLogger(PlatOnClient.class);
    private static final ReentrantReadWriteLock WEB3J_CONFIG_LOCK = new ReentrantReadWriteLock();

    private Map<Web3j,String> web3jMap=new HashMap<>();
    private Web3j currentValidWeb3j;
    private String currentValidAddress;

    @Value("${platon.web3j.addresses}")
    private List<String> web3jAddresses;

    // 节点合约接口
    private NodeContract nodeContract;
    public NodeContract getNodeContract(){return nodeContract;}

    @PostConstruct
    public void init(){
        // 初始化所有web3j实例
        try {
            WEB3J_CONFIG_LOCK.writeLock().lock();
            web3jAddresses.forEach(address->{
                Web3j web3j = Web3j.build(new HttpService(address));
                web3jMap.put(web3j,address);
                if(currentValidWeb3j==null) currentValidWeb3j=web3j;
                if(currentValidAddress==null) currentValidAddress=address;
            });
        }catch (Exception e){
        	logger.error("web3j error{}", e);
        }finally {
            WEB3J_CONFIG_LOCK.writeLock().unlock();
        }
        // 更新合约
        updateContract();
        // 更新有效web3j实例列表
        updateCurrentValidWeb3j();
    }

    private void updateContract(){
        nodeContract = NodeContract.load(currentValidWeb3j);
    }

    public Web3j getWeb3j(){
        WEB3J_CONFIG_LOCK.readLock().lock();
        try{
            return currentValidWeb3j;
        }catch (Exception e){
            logger.error("web3j error{}", e);
        }finally {
            WEB3J_CONFIG_LOCK.readLock().unlock();
        }
        return null;
    }

    public String getWeb3jAddress(){
        return currentValidAddress;
    }


    public void updateCurrentValidWeb3j(){
        WEB3J_CONFIG_LOCK.writeLock().lock();
        Web3j originWeb3j = currentValidWeb3j;
        try {
            // 检查currentValidWeb3j连通性
            try {
                if(currentValidWeb3j==null) throw new ContractInvokeException("currentValidWeb3j需要初始化！");
                currentValidWeb3j.platonBlockNumber().send();
                updateContract();
            } catch (Exception e1) {
                logger.info("当前Web3j实例({})无效，重新选举Web3j实例！",currentValidWeb3j);
                // 连不通，则需要更新
                web3jMap.forEach((web3j,address)->{
                    try {
                        web3j.platonBlockNumber().send();
                        currentValidWeb3j=web3j;
                        currentValidAddress=address;
                    } catch (IOException e2) {
                        logger.info("候选Web3j实例({})无效！",web3j);
                    }
                });
                if(originWeb3j==currentValidWeb3j){
                    logger.info("当前所有候选Web3j实例均无法连通！");
                }
            }
        }finally {
            WEB3J_CONFIG_LOCK.writeLock().unlock();
        }
    }

    /**
     * web3j实例保活
     */
    @Scheduled(cron = "0/30 * * * * ?")
    protected void keepAlive () {
        logger.debug("*** In the detect task *** ");
        try {
            updateCurrentValidWeb3j();
        } catch (Exception e) {
            logger.error("detect exception:{}", e);
        }
        logger.debug("*** End the detect task *** ");
    }

    public PrepareQC getPrepareQC(BigInteger blockNumber) throws InterruptedException {
        return getPrepareQC(getWeb3jAddress(),blockNumber);
    }

    private static final Random random = new Random();
    public static PrepareQC getPrepareQC(String web3jAddress, BigInteger blockNumber) throws InterruptedException {
        RpcParam p = new RpcParam("2.0","platon_getPrepareQC", Collections.singletonList(blockNumber),random.nextInt(Integer.MAX_VALUE));
        String param = JSON.toJSONString(p);
        PrepareQC prepareQC = null;
        while (true) try {
            Web3Response response = HttpUtil.post(web3jAddress, param, Web3Response.class);
            prepareQC = JSON.parseObject(response.getResult(), PrepareQC.class);
            break;
        } catch (HttpRequestException e) {
            logger.error("查询[platon_getPrepareQC]错误,将重试:we3j={},error={}", web3jAddress, e.getMessage());
        } catch (Exception e) {
            logger.error("查询[platon_getPrepareQC]错误,将重试:{}", e.getMessage());
            TimeUnit.SECONDS.sleep(1);
        }
        return prepareQC;
    }
}