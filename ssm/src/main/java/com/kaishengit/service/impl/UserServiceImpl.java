package com.kaishengit.service.impl;

import com.github.pagehelper.PageInfo;
import com.kaishengit.entity.User;
import com.kaishengit.mapper.UserMapper;
import com.kaishengit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SPL on 2017/7/14 0014.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {

        return userMapper.findAll();
    }

    @Override
    public void add(User user) {
        userMapper.add(user);
    }

    @Override
    public void delById(Integer id) {
        userMapper.delById(id);
    }

    @Override
    public User findById(Integer id) {

        return userMapper.findById(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    //分页搜索查询
    @Override
    public PageInfo<User> findByParam(Integer pageNo, String userName, String address, Integer min, Integer max) {

        Map<String,Object> serchParam = new HashMap<>();

        serchParam.put("pageNum",pageNo);
        serchParam.put("pageSize",6);

        serchParam.put("userName",userName);
        serchParam.put("address",address);
        serchParam.put("min",min);
        serchParam.put("max",max);

        List<User> userList = userMapper.findByParam(serchParam);

        return new PageInfo<>(userList);

    }



}
