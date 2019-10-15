package com.platon.statistic.util;

import com.alibaba.fastjson.JSON;
import com.platon.statistic.bean.PrepareQC;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class PlatOnClientTest {

    private static final Logger log = LoggerFactory.getLogger(PlatOnClientTest.class);

    @Test
    public void getPrepareQC() throws InterruptedException {
        String address = "http://192.168.112.172:6789";
        PrepareQC pq = PlatOnClient.getPrepareQC(address, BigInteger.ONE);
        log.error("{}", JSON.toJSONString(pq,true));
    }
}
