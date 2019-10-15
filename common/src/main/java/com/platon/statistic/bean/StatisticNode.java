package com.platon.statistic.bean;

import com.platon.statistic.dao.entity.Node;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @Auther: Chendongming
 * @Date: 2019/8/14 12:02
 * @Description: 节点实体扩展类
 */
@Data
public class StatisticNode extends Node {

     public StatisticNode(){
         super();
         Date date = new Date();
         this.setCreateTime(date);
         this.setUpdateTime(date);
          /* 初始化默认值 */
        this.setSignCount(0L);
        this.setStatValidatorTime(0);
     }

     public void calcSignRate(Long consensusPeriodBlockCount){
         // (实际节点区块签名数/[成为验证节点次数*共识轮目标区块数])*100%
         BigDecimal sigCount = BigDecimal.valueOf(this.getSignCount());
         BigDecimal denominator = BigDecimal.valueOf(this.getStatValidatorTime()*consensusPeriodBlockCount);
         if(BigDecimal.ZERO.compareTo(denominator)==0){
             this.setSignRate(0d);
         }
         else{
             BigDecimal signRate = sigCount.divide(denominator,4, RoundingMode.FLOOR).multiply(BigDecimal.valueOf(100));
             this.setSignRate(signRate.doubleValue());
         }
     }
}
