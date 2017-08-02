package com.kaishengit.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.kaishengit.pojo.Account;
import com.kaishengit.util.HibernateUtil;

/**
 * Hibernate基本增删改查
 * @author SPL
 *
 */
public class Test {
	
	/**
	 * 新增
	 */
	@org.junit.Test
	public void save() {
		 //读取配置文件(从classpath中读取名称为hibernate.cfg.xml的配置文件)
        Configuration configuration = new Configuration().configure();
        //创建SessionFactory
        //SessionFactory sessionFactory = configuration.buildSessionFactory();//不推荐使用
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //创建Session
        Session session = sessionFactory.getCurrentSession();
        //开启事务
        Transaction transaction = session.getTransaction();
        transaction.begin();

        Account account = new Account();
        account.setUsername("lilith");
        account.setAddress("上海");
        account.setAge(23);

        session.save(account);


        //关闭事务（提交 | 回滚）
        transaction.commit();

	}
	
	/**
	 * 删除，只能使用对象删除
	 */
	@org.junit.Test
	public void delete() {
		Session session = HibernateUtil.getSession();
		//开启事务
		session.beginTransaction();
		//获取对象，删除
		Account account = (Account) session.get(Account.class, 6);
		session.delete(account);
		
		//提交事务，会自动关闭session
		session.getTransaction().commit();
		
	}
	
	/**
	 * 更新
	 */
	@org.junit.Test
	public void update() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		Account account = (Account) session.get(Account.class, 2);//根据主键查询，持久态
		account.setUsername("李大伟");
		
		session.getTransaction().commit();
	}
	
	
	/**
	 * 根据主键查询
	 * session.get()
	 */
	@org.junit.Test
	public void findById() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		Account acc = (Account) session.get(Account.class, 5);
		System.out.println(acc);
		
		session.getTransaction().commit();
	}
	
	/**
	 * 查询全部Account
	 */
	@org.junit.Test
	public void findAll() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		String hql = "from Account where username = :name";
		Query quary = session.createQuery(hql);
		quary.setParameter("name", "lilith");
		List<Account> accountList = quary.list();
		
		for(Account account : accountList) {
			System.out.println(account.getId());
		}
		
		session.getTransaction().commit();
	}
	
}
