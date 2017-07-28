package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.Chance;
import com.kaishengit.crm.entity.ChanceExample;
import com.kaishengit.crm.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChanceMapper {
    long countByExample(ChanceExample example);

    int deleteByExample(ChanceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Chance record);

    int insertSelective(Chance record);

    List<Chance> selectByExample(ChanceExample example);

    Chance selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Chance record, @Param("example") ChanceExample example);

    int updateByExample(@Param("record") Chance record, @Param("example") ChanceExample example);

    int updateByPrimaryKeySelective(Chance record);

    int updateByPrimaryKey(Chance record);

    List<Chance> selectByParam(@Param("user") User user, @Param("keyword") String keyword);

    Integer findIdByCustId(@Param("custId") Integer custId);
}