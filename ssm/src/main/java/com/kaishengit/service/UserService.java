package com.kaishengit.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.User;

import java.util.List;

/**
 * Created by SPL on 2017/7/14 0014.
 */

public interface UserService {

    List<User> findAll();

    void add(User user);

    void delById(Integer id);

    User findById(Integer id);

    void update(User user);

    PageInfo<User> findByParam(Integer pageNo, String userName, String address, Integer min, Integer max);
}
