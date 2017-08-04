package com.kaishengit.service;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.Condition;
import com.kaishengit.util.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void findAll() throws Exception {

        List<Account> accountList =  accountService.findAll();

        for(Account account:accountList){
            System.out.println(account.getUserName());
        }

    }

    @Test
    public void findAllByCondention() throws Exception {

        //参数
        Condition condition = new Condition("age",23,"eq");
        Condition condition1 = new Condition("address","北京","eq");

        List<Account> accountList =  accountService.findAllByCondition(condition, condition1);

        for(Account account:accountList){
            System.out.println(account.getUserName());
        }

    }
    @Test
    public void findByPage(){
        Condition condition = new Condition("age",23,"eq");
        Condition condition2 = new Condition("address","北京","eq");
        Page<Account> page = accountService.findAllByConditionAndPage(0, condition);
        List<Account> accountList = page.getItems();
        for(Account account:accountList){
            System.out.println(account.getUserName());
        }
    }

}