package com.platon.statistic.bean;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class RpcParam {
    private int id;
    private String jsonrpc;
    private String method;
    private List<BigInteger> params;

    public RpcParam(String jsonrpc, String method, List<BigInteger> params, int id) {
        this.id = id;
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.params = params;
    }
}
