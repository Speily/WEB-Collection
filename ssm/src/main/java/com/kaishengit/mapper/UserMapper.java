package com.kaishengit.mapper;

import com.kaishengit.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by SPL on 2017/7/14 0014.
 */
public interface UserMapper {


    List<User> findAll();

    void add(User user);

    void delById(Integer id);

    User findById(Integer id);

    void update(User user);

    List<User> findByParam(Map<String, Object> serchParam);
}
