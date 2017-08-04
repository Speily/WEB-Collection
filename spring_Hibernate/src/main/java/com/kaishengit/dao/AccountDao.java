package com.kaishengit.dao;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.Condition;
import com.kaishengit.util.Page;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends BaseDao<Account,Integer>{
    /**
     * user别名u
     * @param pageNum
     * @param perSize
     * @param id
     * @param desc
     * @param conditions
     * @return
     */
    public Page<Account>  findByConditionAndPage(Integer pageNum, Integer perSize, String id, String desc, Condition[] conditions){
        Criteria criteria = getSession().createCriteria(Account.class);
        criteria.createAlias("user","u");
        return findByConditionAndPage(pageNum,5,"id","desc", conditions,criteria);
    }
}
