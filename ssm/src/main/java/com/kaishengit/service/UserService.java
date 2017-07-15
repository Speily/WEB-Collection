package com.kaishengit.service;

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
}
