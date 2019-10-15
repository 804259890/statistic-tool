package com.platon.statistic.cmd;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.JCommander;
import com.google.common.collect.Sets;
import com.platon.statistic.arg.Params;
import com.platon.statistic.service.BlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Auther: Chendongming
 * @Date: 2019/7/16 12:49
 * @Description:
 */
@Slf4j
@Component
public class CmdRunner implements CommandLineRunner {

    @Autowired
    private BlockService blockService;

    @Override
    public void run(String... args) throws Exception {
        Params param = parseArgs(args);
        log.info("命令行参数: {}", JSON.toJSONString(param,true));
        blockService.init(param);
        while (true) {
            if (!blockService.collect()) break;
        }
    }

    private Params parseArgs(String[] cliArgs) {
        Params param = new Params();
        JCommander jc = JCommander.newBuilder().addObject(param).build();
        jc.setProgramName("java -jar statistic.jar");
        try {
            Set<String> args = Sets.newHashSet(cliArgs);
            if (args.contains("--help")||args.contains("-h"))  printHelp(jc);
            jc.parse(cliArgs);
            if(
                param.getEndTime()==null
                &&(param.getPeriods()==null||param.getPeriods().isEmpty())
                &&param.getTimestamp()==null
            ) printHelp(jc);
        } catch (Exception e) {
            jc.getConsole().println("无法解析参数: " + e.getClass() + " -> " + e.getMessage());
            printHelp(jc);
        }
        return param;
    }

    private void printHelp(JCommander jc){
        jc.usage();
        System.exit(0);
    }
}
