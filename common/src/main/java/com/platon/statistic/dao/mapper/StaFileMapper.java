package com.platon.statistic.dao.mapper;

import com.platon.statistic.dao.entity.StaFile;
import com.platon.statistic.dao.entity.StaFileExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface StaFileMapper {
    long countByExample(StaFileExample example);

    int deleteByExample(StaFileExample example);

    int insert(StaFile record);

    int insertSelective(StaFile record);

    List<StaFile> selectByExample(StaFileExample example);

    int updateByExampleSelective(@Param("record") StaFile record, @Param("example") StaFileExample example);

    int updateByExample(@Param("record") StaFile record, @Param("example") StaFileExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sta_file
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<StaFile> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sta_file
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<StaFile> list, @Param("selective") StaFile.Column ... selective);
}