package com.kaishengit.crm.mapper;

import java.util.List;

import com.kaishengit.crm.entity.Dept;

public interface DeptMapper {

    List<Dept> findAll();

    void add(Dept dept);
}