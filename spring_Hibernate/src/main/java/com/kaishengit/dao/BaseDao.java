package com.kaishengit.dao;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.Condition;
import com.kaishengit.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDao<T , ID extends Serializable> {

    private Class<?> clazz;

    public BaseDao(){
        //Class<?> sonClazz = this.getClass();//获取子类的class对象
        //clazz.getSuperclass();//获取父类的class  Father
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();//获取泛型父类的class Father<Account>
        Type[] types = parameterizedType.getActualTypeArguments();//获取泛型参数数组
        clazz = (Class<?>) types[0];

    }

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 获取Session
     */
    protected Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    /**
     * 保存
     * @param entity 对象
     */
    public void save(T entity){
        getSession().saveOrUpdate(entity);
    }

    /**
     * 根据主键查找
     * @param id 主键
     * @return
     */
    public T findById(ID id){
        return (T) getSession().get(clazz,id);
    }

    /**
     * 删除
     * @param entity 对象
     */
    public void  delete(T entity){
        getSession().delete(entity);
    }

    /**
     * 根据主键删除
     * @param id 主键
     */
    public void deleteById(ID id){
        getSession().delete(findById(id));
    }

    /**
     * 查询全部
     * @return List<T>
     */
    public List<T> findAll(){
        Criteria criteria = getSession().createCriteria(clazz);
        return criteria.list();
    }

    /**
     * 根据条件查询
     * @param columnName 表中对应列名
     * @param value 值
     * @return List<T>
     */
    public List<T> findByParam(String columnName ,Object value){
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.eq(columnName,value));
        return criteria.list();
    }

    /**
     * criteria条件
     * @param condition
     * @return Criterion
     */
    public Criterion bulidRestrictionsWithCondition(Condition condition){

        //处理（or）_or_的情况

        String propertyName  = condition.getPropertyName();
        String type = condition.getType();
        Object value = condition.getValue();

        if(propertyName.contains("_or_")){
            String[] propertys = propertyName.split("_or_");
            Disjunction disjunction = Restrictions.disjunction();
            for(String property : propertys){
                disjunction.add( bulidRestrictions(property,value,type));
            }
            return disjunction;

        } else {
            return bulidRestrictions(propertyName,value,type);
        }
    }

    /**
     * criteria条件
     * @param property 属性
     * @param value 值
     * @param type 类型
     * @return
     */
    private Criterion bulidRestrictions(String property, Object value, String type) {

        Criterion criterion = null;

        if("eq".equals(type)){
            criterion = Restrictions.eq(property,value);
        }else if(type.equalsIgnoreCase("lt")) {
            return Restrictions.lt(property,value);
        } else if(type.equalsIgnoreCase("gt")) {
            return Restrictions.gt(property,value);
        } else if(type.equalsIgnoreCase("le")) {
            return Restrictions.le(property,value);
        } else if(type.equalsIgnoreCase("ge")) {
            return Restrictions.ge(property,value);
        }else if ("like".equals(type)){
            criterion = Restrictions.like(property,value.toString(), MatchMode.ANYWHERE);//like
        }
        return criterion;
    }

    /**
     * 多条件查询
     * @param conditions 条件
     * @return List<T>
     */
    public List<T> findByCondition(Condition... conditions){
        Criteria criteria = getSession().createCriteria(clazz);
        for(Condition condition : conditions){
            criteria.add(bulidRestrictionsWithCondition(condition));
        }
        return criteria.list();

    }

    /**
     * 数量
     * @param conditions
     * @return
     */
    public Long count(Criteria criteria,Condition[] conditions) {

        for(Condition condition : conditions){
            criteria.add(bulidRestrictionsWithCondition(condition));
        }

        //获取riteria传进来时的状态
        ResultTransformer resultTransformer = criteria.ROOT_ENTITY;

        criteria.setProjection(Projections.rowCount());

        Long count =  (Long) criteria.uniqueResult();

        //还原riteria至传进来时的状态
        criteria.setProjection(null);
        criteria.setResultTransformer(resultTransformer);

        return count;
    }

    /**
     * 搜索分页查询（+别名）
     * @param pageNum 当前页码
     * @param perSize
     * @param id
     * @param desc
     * @param conditions
     * @param criteria +别名
     * @return
     */
    public Page<T> findByConditionAndPage(Integer pageNum, Integer perSize, String id, String desc, Condition[] conditions,Criteria criteria) {

        int total = count(criteria,conditions).intValue();//总页数

        Page<T> page = new Page<T>(pageNum,perSize,total);

        for(Condition condition : conditions){
            criteria.add(bulidRestrictionsWithCondition(condition));
        }

        //分页
        criteria.setFirstResult(page.getPageStart());
        criteria.setMaxResults(perSize);

        List<T> result = criteria.list();

        page.setItems(result);

        return page;
    }

    /**
     * 搜索分页查询
     * @param pageNum 当前页码
     * @param perSize
     * @param id
     * @param desc
     * @param conditions
     * @return
     */
    public Page<T> findByConditionAndPage(Integer pageNum, Integer perSize, String id, String desc, Condition[] conditions) {

        Criteria criteria = getSession().createCriteria(clazz);

        return findByConditionAndPage(pageNum, perSize, id, desc, conditions,criteria);
    }
}
