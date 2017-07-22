package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.User;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public interface CustomerService {

    void newMyCust(User user, Customer cust);

    void newPubCust(User user, Customer cust);

    PageInfo<Customer> findMyCustList(Map<String, Object> queryParam,User user);

    List<String> findTradeList();

    List<String> findSourceList();

    Customer findById(Integer id);

    void update(Customer customer);

    void del(Integer id);

    void toPublic(Customer customer);

    void turnToSomeone(Customer customer, Integer userId,User user);

    void exportExcel(User user, OutputStream outputStream);
}
