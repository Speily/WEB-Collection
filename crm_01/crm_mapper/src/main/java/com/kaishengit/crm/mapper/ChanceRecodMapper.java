package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.ChanceRecod;
import com.kaishengit.crm.entity.ChanceRecodExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChanceRecodMapper {
    long countByExample(ChanceRecodExample example);

    int deleteByExample(ChanceRecodExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChanceRecod record);

    int insertSelective(ChanceRecod record);

    List<ChanceRecod> selectByExampleWithBLOBs(ChanceRecodExample example);

    List<ChanceRecod> selectByExample(ChanceRecodExample example);

    ChanceRecod selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChanceRecod record, @Param("example") ChanceRecodExample example);

    int updateByExampleWithBLOBs(@Param("record") ChanceRecod record, @Param("example") ChanceRecodExample example);

    int updateByExample(@Param("record") ChanceRecod record, @Param("example") ChanceRecodExample example);

    int updateByPrimaryKeySelective(ChanceRecod record);

    int updateByPrimaryKeyWithBLOBs(ChanceRecod record);

    int updateByPrimaryKey(ChanceRecod record);
}