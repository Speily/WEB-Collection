package com.kaishengit.service;

import com.kaishengit.dao.AccountDao;
import com.kaishengit.pojo.Account;
import com.kaishengit.util.Condition;
import com.kaishengit.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountDao accountDao;


    public List<Account> findAll(){
        return accountDao.findAll();
    }

    public List<Account> findAllByCondition(Condition... conditions){
        return accountDao.findByCondition(conditions);
    }

    public Page<Account> findAllByConditionAndPage(Integer pageNum,Condition... conditions){

        return accountDao.findByConditionAndPage(pageNum,5,"id","desc", conditions);

    }


}
