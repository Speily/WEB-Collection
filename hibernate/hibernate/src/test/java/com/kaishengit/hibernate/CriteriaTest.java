package com.kaishengit.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.HibernateUtil;

public class CriteriaTest {
	
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
	 * Criteria��ͨ��ѯ
	 */
	@org.junit.Test
	public void criteriaTest() {
		
		Criteria criteria = session.createCriteria(Account.class);//��ȡCriteria���󣨱�׼Criteria��
		
		//criteria.add(Restrictions.eq("username", "lilith"));//where������ѯ����׼֮�����Լ��������Restrictions��
		
		//criteria.add(Restrictions.between("id", 3, 5));
		
		criteria.add(Restrictions.like("username", "l%"));//ģ����ѯ���'%'
		
		//��ҳ
		criteria.setFirstResult(1);
		criteria.setMaxResults(2);
		
		//criteria.add(Restrictions.and(Restrictions.eq("username", "lilith"), Restrictions.eq("id", 4)));
		
		List<Account> accountList = criteria.list(); 
		for(Account acc : accountList) {
			System.out.println(acc);
			System.out.println("-----------------");
		}
		
		
		/*//1.��ѯ����List
		List<Account> accountList = criteria.list();
		for(Account acc : accountList) {
			System.out.println(acc);
			System.out.println("-----------------");
		}*/
		
	}
	
	
	/**
	 * Criteria�ۺϺ���
	 * uniqueResult()��ȡ���
	 */
	@Test
	public void count() {
		Criteria criteria = session.createCriteria(Account.class);
		
		//count(*)
//		criteria.setProjection(Projections.rowCount());
//		Object result = criteria.uniqueResult();
//		System.out.println("count(*)-> "+result);
		
		//count(age)
//		criteria.setProjection(Projections.count("age"));
//		Object result = criteria.uniqueResult();
//		System.out.println("count(age)-> "+result);
		
		//��ѯ��������ʱʹ��ProjectionList
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.rowCount());
		projectionList.add(Projections.max("id"));
		criteria.setProjection(projectionList);
		Object[] results = (Object[]) criteria.uniqueResult();
		for(Object rs : results) {
			System.out.println(rs);
		}
	}
	
	/**
	 * ʹ��ԭ��SQL
	 */
	@Test
	public void sqlTest() {
		
		String sql2 = "select * from account where id = :id";
		SQLQuery sqlQuery2 = session.createSQLQuery(sql2).addEntity(Account.class);
		sqlQuery2.setParameter("id", 5);
		Account acc = (Account) sqlQuery2.uniqueResult();
		System.out.println(acc);
		
		System.out.println("-------------");
		
		String sql3 = "select * from account where username = :name";
		SQLQuery sqlQuery3 = session.createSQLQuery(sql3).addEntity(Account.class);
		sqlQuery3.setParameter("name", "lilith");
		List<Account> accList = (List<Account>) sqlQuery3.list();
		for(Account acc2:accList) {
			System.out.println(acc2);
		}
		
		System.out.println("-------------");
		
		String sql = "select count(*) from account";
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		Object res = sqlQuery.uniqueResult();
		System.out.println(res);
		
		System.out.println("-------------");
		
		String sql4 = "select count(*),max(id) from account";
		SQLQuery sqlQuery4 = session.createSQLQuery(sql4);
		Object[] res4s = (Object[]) sqlQuery4.uniqueResult();
		for(Object res4 : res4s) {
			System.out.println(res4);
		}
	}
	
	
}
