package com.kaishengit.hibernate;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

import com.kaishengit.util.HibernateUtil;

public class BaseTest {
	
    protected Session session;
	
	@Before
	public void before() {
		session = HibernateUtil.getSession();
		session.beginTransaction();
	}
	
	@After
	public void after() {
		session.getTransaction().commit();
	}
}
