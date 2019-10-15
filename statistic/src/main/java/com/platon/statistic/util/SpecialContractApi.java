package com.platon.statistic.util;

import com.alibaba.fastjson.JSON;
import com.platon.statistic.enums.InnerContractAddrEnum;
import com.platon.statistic.excption.ContractInvokeException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.Node;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.PlatonCall;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;
import org.web3j.utils.PlatOnUtil;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Auther: Chendongming
 * @Date: 2019/8/31 11:00
 * @Description:
 */
@Component
public class SpecialContractApi {
    private static Logger logger = LoggerFactory.getLogger(SpecialContractApi.class);

    /**
     * 查询结算周期历史验证人队列
     */
    public static final int GET_HISTORY_VERIFIER_LIST_FUNC_TYPE = 1106;
    /**
     * 查询历史共识周期的验证人列
     */
    public static final int GET_HISTORY_VALIDATOR_LIST_FUNC_TYPE = 1107;
    /**
     * 获取可用和锁仓余额
     */
    public static final int GET_RESTRICTING_BALANCE_FUNC_TYPE = 4101;
    /**
     * 获取提案结果
     */
    public static final int GET_PROPOSAL_RES_FUNC_TYPE = 2105;

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
	private BaseResponse<String> rpc(Web3j web3j,Function function,DefaultBlockParameter blockParameter,String from,String to) throws ContractInvokeException {
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
    public List<Node> getHistoryVerifierList(Web3j web3j, BigInteger blockNumber) throws ContractInvokeException {
        return nodeCall(web3j,blockNumber,GET_HISTORY_VERIFIER_LIST_FUNC_TYPE);
    }

    /**
     * 根据区块号获取共识周期验证人列表
     * @param blockNumber
     * @return
     * @throws Exception
     */
    public List<Node> getHistoryValidatorList(Web3j web3j,BigInteger blockNumber) throws ContractInvokeException {
        return nodeCall(web3j,blockNumber,GET_HISTORY_VALIDATOR_LIST_FUNC_TYPE);
    }

    /**
     * 根据区块号获取节点列表
     * @return
     * @throws Exception
     */
	private List<Node> nodeCall(Web3j web3j,BigInteger blockNumber,int funcType) throws ContractInvokeException {
        final Function function = new Function(
            funcType,
            Collections.singletonList(new Uint256(blockNumber)),
            Collections.singletonList(new TypeReference<Utf8String>() {})
        );
        BaseResponse<String> br = rpc(web3j,function,DefaultBlockParameter.valueOf(blockNumber), InnerContractAddrEnum.NODE_CONTRACT.getAddress(),InnerContractAddrEnum.NODE_CONTRACT.getAddress());
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
}
