package com.platon.statistic.bean;

import lombok.Data;

import java.math.BigInteger;

@Data
public class PrepareQC {
    private String blockHash;
    private int blockIndex;
    private BigInteger blockNumber;
    private int epoch;
    private String signature;
    private String validatorSet;
    private BigInteger viewNumber;
}
