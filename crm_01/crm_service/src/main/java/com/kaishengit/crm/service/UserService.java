package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.User;

import java.util.List;

/**
 * Created by SPL on 2017/7/17 0017.
 */
public interface UserService {
    void add(User user, Integer[] deptId);
}
