package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.entity.UserDept;
import com.kaishengit.crm.mapper.UserDeptMapper;
import com.kaishengit.crm.mapper.UserMapper;
import com.kaishengit.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by SPL on 2017/7/18 0018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDeptMapper userDeptMapper;
    /*
     * 添加员工
     */
    @Override
    @Transactional
    public void add(User user, Integer[] deptIds) {
        user.setCreateTime(new Date());
        userMapper.insert(user);

        //添加员工和部门关系
        for(Integer deptId:deptIds){
            UserDept userDept = new UserDept();
            userDept.setDeptId(deptId);
            userDept.setUserId(user.getId());
            userDeptMapper.insert(userDept);
        }
    }
}
