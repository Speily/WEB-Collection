package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by SPL on 2017/7/17 0017.
 */
public interface UserMapper {

    List<User> findAll();

    void insert(User user);

    void delById(@Param("id")Integer id);

    Long countByDeptId(@Param("deptId") Integer deptId);

    List<User> finByDeptId(@Param("deptId") Integer deptId);

    Long count();

    User findByTelLoadDept(String tel);


    void update(User user);

}
