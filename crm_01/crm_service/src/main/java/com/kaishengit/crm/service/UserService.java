package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.User;

import java.util.List;

/**
 * Created by SPL on 2017/7/17 0017.
 */
public interface UserService {
    void add(User user, Integer[] deptId);

    void dellById(Integer userId);

    Long countByDeptId(Integer id);

    List<User> findByDeptId(Integer id);

    Long count();

    User findByTelLoadDept(String tel, String password);

    void update(User user, String oldPassword, String newPassword);

    List<User> findAllUser();

    User findByUserId(Integer userId);
}
