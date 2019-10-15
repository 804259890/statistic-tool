package com.platon.statistic.util;

import com.alibaba.fastjson.JSON;
import com.platon.statistic.bean.PrepareQC;
import com.platon.statistic.bean.RpcParam;
import com.platon.statistic.bean.Web3Response;
import com.platon.statistic.enums.InnerContractAddrEnum;
import com.platon.statistic.excption.ContractInvokeException;
import com.platon.statistic.excption.HttpRequestException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.Node;
import org.web3j.platon.contracts.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.PlatonCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;
import org.web3j.utils.PlatOnUtil;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: dongqile
 * Date: 2018/10/25
 * Time: 14:42
 */
@Data
public class PlatOnClient {
    private static Logger logger = LoggerFactory.getLogger(PlatOnClient.class);
    private static final ReentrantReadWriteLock WEB3J_CONFIG_LOCK = new ReentrantReadWriteLock();

    private Map<Web3j,String> web3jMap=new HashMap<>();
    private Web3j currentValidWeb3j;
    private String currentValidAddress;

    private List<String> web3jAddresses;
    public void setWeb3jAddresses(List<String> web3jAddresses){
        this.web3jAddresses = web3jAddresses;
    }

    // 节点合约接口
    private NodeContract nodeContract;
    public NodeContract getNodeContract(){return nodeContract;}

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
    public void keepAlive () {
        logger.debug("*** In the detect task *** ");
        try {
            updateCurrentValidWeb3j();
        } catch (Exception e) {
            logger.error("detect exception:{}", e);
        }
        logger.debug("*** End the detect task *** ");
    }


    /**
     * 查询结算周期历史验证人队列
     */
    public static final int GET_HISTORY_VERIFIER_LIST_FUNC_TYPE = 1106;
    /**
     * 查询历史共识周期的验证人列
     */
    public static final int GET_HISTORY_VALIDATOR_LIST_FUNC_TYPE = 1107;

    private static final String BLANK_RES = "结果为空!";
    private static final String INVOKE_FAIL = "调用合约失败:";

    /**
     * rpc调用接口
     * @param web3j
     * @param function
     * @param blockParameter
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private BaseResponse<String> rpc(Web3j web3j, Function function, DefaultBlockParameter blockParameter, String from, String to) throws ContractInvokeException {
        BaseResponse<String> br;
        try {
            br = new RemoteCall<>((Callable<BaseResponse<String>>) () -> {
                String encodedFunction = PlatOnUtil.invokeEncode(function);
                PlatonCall ethCall = web3j.platonCall(Transaction.createEthCallTransaction(from,to,encodedFunction),blockParameter).send();
                String value = ethCall.getValue();
                if("0x".equals(value)){
                    // 证明没数据,返回空响应
                    return new BaseResponse<>();
                }
                String decodedValue = new String(Numeric.hexStringToByteArray(value));
                return JSONUtil.parseObject(decodedValue, BaseResponse.class);
            }).send();
        } catch (Exception e) {
            throw new ContractInvokeException(e.getMessage());
        }
        return br;
    }

    /**
     * 根据区块号获取结算周期验证人列表
     * @param blockNumber
     * @return
     * @throws Exception
     */
    public List<Node> getHistoryVerifierList(BigInteger blockNumber) throws ContractInvokeException {
        return nodeCall(blockNumber,GET_HISTORY_VERIFIER_LIST_FUNC_TYPE);
    }

    /**
     * 根据区块号获取共识周期验证人列表
     * @param blockNumber
     * @return
     * @throws Exception
     */
    public List<Node> getHistoryValidatorList(BigInteger blockNumber) throws ContractInvokeException {
        return nodeCall(blockNumber,GET_HISTORY_VALIDATOR_LIST_FUNC_TYPE);
    }

    /**
     * 根据区块号获取节点列表
     * @return
     * @throws Exception
     */
    private List<Node> nodeCall(BigInteger blockNumber,int funcType) throws ContractInvokeException {
        final Function function = new Function(
                funcType,
                Collections.singletonList(new Uint256(blockNumber)),
                Collections.singletonList(new TypeReference<Utf8String>() {})
        );
        BaseResponse<String> br = rpc(getWeb3j(),function,DefaultBlockParameter.valueOf(blockNumber), InnerContractAddrEnum.NODE_CONTRACT.getAddress(),InnerContractAddrEnum.NODE_CONTRACT.getAddress());
        if(br==null||br.data==null){
            throw new ContractInvokeException(String.format("【查询验证人出错】函数类型:%s,区块号:%s,返回为空!%s",String.valueOf(funcType),blockNumber, JSON.toJSONString(Thread.currentThread().getStackTrace())));
        }
        if(br.isStatusOk()){
            String data = br.data;
            if(StringUtils.isBlank(data)){
                throw new ContractInvokeException(BLANK_RES);
            }
            data = data.replace("\"Shares\":null","\"Shares\":\"0x0\"");
            List<Node> result;
            result = JSONUtil.parseArray(data, Node.class);
            return result;
        }else{
            String msg = JSON.toJSONString(br,true);
            throw new ContractInvokeException(String.format("【查询验证人出错】函数类型:%s,区块号:%s,返回数据:%s",funcType,blockNumber.toString(),msg));
        }
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