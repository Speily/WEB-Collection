package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerMapper customerMapper;

    @Value("#{'${cust.trade}'.split(',')}")
    private List<String> tradeList;
    @Value("#{'${cust.source}'.split(',')}")
    private List<String> sourceList;

    /**
     * 新增客户
     * @param user
     * @param cust
     */
    @Override
    public void newMyCust(User user, Customer cust) {
        Integer userId = user.getId();
        cust.setUserId(userId);
        cust.setCreateTime(new Date());
        customerMapper.insert(cust);
    }

    @Override
    public PageInfo<Customer> findMyCustList(Map<String, Object> queryParam,User user) {

        Integer pageNo = (Integer) queryParam.get("pageNo");
        String keyword =(String) queryParam.get("keyword");
        if(StringUtils.isNotBlank(keyword)){
            keyword =  "%" + keyword + "%" ;
        }
        PageHelper.startPage(pageNo,8);

        List<Customer> customerList = customerMapper.selectByParam(user,keyword);

        PageInfo<Customer> pageInfo = new PageInfo<>(customerList);

        return pageInfo;
    }

    @Override
    public List<String> findTradeList() {
        return tradeList;
    }


    @Override
    public List<String> findSourceList() {
        return sourceList;
    }

    /**
     * 根据id查询客户
     * @param id
     * @return
     */
    @Override
    public Customer findById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Customer customer) {
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Override
    public void del(Integer id) {
        // TODU 删除客户相关项
        customerMapper.deleteByPrimaryKey(id);
    }


}
