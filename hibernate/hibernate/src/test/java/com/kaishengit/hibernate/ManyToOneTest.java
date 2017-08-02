package com.kaishengit.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.kaishengit.pojo.Address;
import com.kaishengit.pojo.User;

public class ManyToOneTest extends BaseTest {
	
	/**
	 * �½��û� �½���ַ  
	 * �����û��͵�ַ
	 */
	@Test
	public void save() {
		
		User user = new User();
		user.setName("������");
		
		Address addr = new Address();
		addr.setCity("��ݸ");
		addr.setAddress("˾���");
		addr.setUser(user);
		
		//�ȱ���һ���ڱ����
		session.save(user);
		session.save(addr);
	}
	
	/**
	 * ��ָ���û���ӵ�ַ
	 */
	@Test
	public void addAddress() {
		
		User user = (User) session.get(User.class, 3);
		
		Address addr = new Address();
		addr.setCity("������");
		addr.setAddress("��̨��");
		addr.setUser(user);
		
		session.save(addr);
	}
	
	/**
	 * �û�id��Ӧ�ĵ�ַ
	 */
	@Test
	public void findAddrById() {
		
		Criteria cri = session.createCriteria(Address.class);
		
		cri.add(Restrictions.eq("user.id", 2));//ONGL�����ϵͼӳ��
		
		List<Address> addrList = cri.list();
		
		for(Address addr : addrList) {
			System.out.println(addr);
		}
		
	}
	/**
	 * �û�������Ӧ�ĵ�ַ
	 */
	@Test
	public void findByName() {
		
		Criteria cri = session.createCriteria(Address.class);//������Ӧ��׼Criteria
		cri.createAlias("user", "u");//��Address�е����Զ���user�����
		
		cri.add(Restrictions.eq("u.name", "������"));//��׼֮�����Լ��������
		
		List<Address> addrList = cri.list();
		
		for(Address addr : addrList) {
			System.out.println(addr);
		}
		
	}
	
	
}
