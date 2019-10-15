package com.platon.statistic.arg;

import com.beust.jcommander.Parameter;
import com.platon.statistic.converter.DateConverter;
import com.platon.statistic.enums.PeriodEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Param {
    @Parameter(names = { "--help","-h"}, description = "打印帮助信息")
    private String help;
    @Parameter(
            names = { "--endTime","-e"},
            description = "统计截止时间,格式: \"yyyy-MM-dd HH:mm:SS\"",
            converter = DateConverter.class
    )
    private Date endTime;
    @Parameter(names = { "--timestamp","-t"},description = "统计截止时间戳(ms)")
    private Long timestamp;
    @Parameter(names = {"--periods","-p"}, description = "统计周期: c-共识周期, s-结算周期,可指定多个例如：-p c,s")
    private List<PeriodEnum> periods;
}