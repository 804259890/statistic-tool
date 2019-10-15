package com.platon.statistic.config;

import com.platon.statistic.util.PlatOnClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BeanConfig {

    @Value("${platon.web3j.addresses}")
    private List<String> web3jAddresses;

    @Bean
    public PlatOnClient platOnClient(){
        PlatOnClient platOnClient = new PlatOnClient();
        platOnClient.setWeb3jAddresses(web3jAddresses);
        platOnClient.init();
        return platOnClient;
    }

    @Bean
    public BlockChainConfig chainConfig() throws InterruptedException {
        BlockChainConfig chainConfig = new BlockChainConfig();
        chainConfig.setClient(platOnClient());
        chainConfig.init();
        return chainConfig;
    }
}
