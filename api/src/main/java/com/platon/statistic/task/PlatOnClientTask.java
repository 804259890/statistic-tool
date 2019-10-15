package com.platon.statistic.task;

import com.platon.statistic.util.PlatOnClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PlatOnClientTask {
    @Autowired
    private PlatOnClient platOnClient;
    @Scheduled(cron = "0/30 * * * * ?")
    public void run(){
        platOnClient.keepAlive();
    }
}
