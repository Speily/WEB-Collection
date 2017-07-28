package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.service.DeptService;
import com.kaishengit.weixin.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SPL on 2017/7/17 0017.
 */
@Service
public class DeptServiceImpl implements DeptService{
    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private WeiXinUtil weiXinUtil;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    @Transactional
    public void add(Dept dept) {
        deptMapper.add(dept);
        //同步到微信
        System.out.println(dept.getId()+"--"+dept.getDeptId()+"--"+dept.getDeptName());
        weiXinUtil.createDept(dept.getId(),dept.getDeptId(),dept.getDeptName());
    }
}
