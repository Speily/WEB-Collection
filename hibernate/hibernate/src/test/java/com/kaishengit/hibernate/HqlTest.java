package com.kaishengit.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kaishengit.util.HibernateUtil;

public class HqlTest {

	private Session session;
	
	@Before
	public void before() {
		session = HibernateUtil.getSession();
		session.beginTransaction();
	}
	
	@After
	public void after() {
		session.getTransaction().commit();
	}
	
	/**
	 * 查询总数，（Hibernate查询不完整数据）
	 */
	@Test
	public void count() {
		String hql = "select count(*) from Account";
		Query query = session.createQuery(hql);
		//query.setParameter("name", "lilith");
		Object obj = query.uniqueResult();
		System.out.println(obj);
	}
	
	@Test
	public void findNameByAge() {
		
		String hql = "select count(*),age from Account where age = :age";
		
		Query query = session.createQuery(hql);
		query.setParameter("age", 23);
		Object[] arrays = (Object[]) query.uniqueResult();
		
		for(Object name : arrays) {
			System.out.println(name);
		}
	} 
	/**
	 * 测试hql转SQL
	 */
	@Test
	public void testSQL() {
		String hql = "select count(*),age from Account where age = :age";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("age",23 );
		//String sql = getSQL(hql, map);
		System.out.println("");
	}
	/**
	 * hql转SQL
	 * @param hql
	 * @param map
	 * @return
	 */
	@Test
	public void getSQL() {
		String hql = "select count(*),age from Account where age = :age and username = :name id = :id";
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", "lilith");
		map.put("age", 23);
		
		List<Object> paramList = new ArrayList<Object>();
		String[] keys = hql.split(":");
		String tittle = keys[0].split("where")[0];
		for(int i = 1;i<keys.length;i++) {
			String key = keys[i].split(" ")[0];
			Object obj = key+"="+map.get(key);
			paramList.add(obj);
		}
		if(paramList.size()>0) {
			String sqlbody = " where " + paramList.toString();
			sqlbody = sqlbody.replace("," , " and ").replace("[", " ").replace("]", " ");
			String sql = tittle + sqlbody;
			System.out.println(sql);
		}
		
		
	}
	@Test
	public void totring() {
		List<String> strList = new ArrayList<String>();
		strList.add("aa");
		strList.add("bb");
		System.out.println(strList.toString());
	}
	
}
