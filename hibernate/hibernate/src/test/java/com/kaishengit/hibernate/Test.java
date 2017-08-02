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
 * Hibernate������ɾ�Ĳ�
 * @author SPL
 *
 */
public class Test {
	
	/**
	 * ����
	 */
	@org.junit.Test
	public void save() {
		 //��ȡ�����ļ�(��classpath�ж�ȡ����Ϊhibernate.cfg.xml�������ļ�)
        Configuration configuration = new Configuration().configure();
        //����SessionFactory
        //SessionFactory sessionFactory = configuration.buildSessionFactory();//���Ƽ�ʹ��
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //����Session
        Session session = sessionFactory.getCurrentSession();
        //��������
        Transaction transaction = session.getTransaction();
        transaction.begin();

        Account account = new Account();
        account.setUsername("lilith");
        account.setAddress("�Ϻ�");
        account.setAge(23);

        session.save(account);


        //�ر������ύ | �ع���
        transaction.commit();

	}
	
	/**
	 * ɾ����ֻ��ʹ�ö���ɾ��
	 */
	@org.junit.Test
	public void delete() {
		Session session = HibernateUtil.getSession();
		//��������
		session.beginTransaction();
		//��ȡ����ɾ��
		Account account = (Account) session.get(Account.class, 6);
		session.delete(account);
		
		//�ύ���񣬻��Զ��ر�session
		session.getTransaction().commit();
		
	}
	
	/**
	 * ����
	 */
	@org.junit.Test
	public void update() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		
		Account account = (Account) session.get(Account.class, 2);//����������ѯ���־�̬
		account.setUsername("���ΰ");
		
		session.getTransaction().commit();
	}
	
	
	/**
	 * ����������ѯ
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
	 * ��ѯȫ��Account
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
