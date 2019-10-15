package com.platon.statistic.dao.mapper;

import com.platon.statistic.dao.entity.StaBase;
import com.platon.statistic.dao.entity.StaBaseExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface StaBaseMapper {
    long countByExample(StaBaseExample example);

    int deleteByExample(StaBaseExample example);

    int insert(StaBase record);

    int insertSelective(StaBase record);

    List<StaBase> selectByExample(StaBaseExample example);

    int updateByExampleSelective(@Param("record") StaBase record, @Param("example") StaBaseExample example);

    int updateByExample(@Param("record") StaBase record, @Param("example") StaBaseExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sta_base
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<StaBase> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sta_base
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<StaBase> list, @Param("selective") StaBase.Column ... selective);
}