package com.platon.statistic.bean;

import lombok.Data;

@Data
public class Web3Response {
    private String jsonrpc;
    private int id;
    private String result;
}