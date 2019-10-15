package com.platon.statistic.dao.mapper;

import com.platon.statistic.bean.StatisticNode;
import com.platon.statistic.dao.entity.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface CustomNodeMapper {
    BigInteger selectMaxNumber();
    List<StatisticNode> selectLatestConsensusNode();
    int batchInsertOrUpdateSelective(@Param("list") List<Node> list, @Param("selective") Node.Column... selective);
}
