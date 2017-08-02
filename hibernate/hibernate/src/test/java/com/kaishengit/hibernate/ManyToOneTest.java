package com.kaishengit.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.kaishengit.pojo.Address;
import com.kaishengit.pojo.User;

public class ManyToOneTest extends BaseTest {
	
	/**
	 * 新建用户 新建地址  
	 * 保存用户和地址
	 */
	@Test
	public void save() {
		
		User user = new User();
		user.setName("张婷婷");
		
		Address addr = new Address();
		addr.setCity("东莞");
		addr.setAddress("司里街");
		addr.setUser(user);
		
		//先保存一，在保存多
		session.save(user);
		session.save(addr);
	}
	
	/**
	 * 给指定用户添加地址
	 */
	@Test
	public void addAddress() {
		
		User user = (User) session.get(User.class, 3);
		
		Address addr = new Address();
		addr.setCity("宝鸡市");
		addr.setAddress("金台区");
		addr.setUser(user);
		
		session.save(addr);
	}
	
	/**
	 * 用户id对应的地址
	 */
	@Test
	public void findAddrById() {
		
		Criteria cri = session.createCriteria(Address.class);
		
		cri.add(Restrictions.eq("user.id", 2));//ONGL对象关系图映射
		
		List<Address> addrList = cri.list();
		
		for(Address addr : addrList) {
			System.out.println(addr);
		}
		
	}
	/**
	 * 用户姓名对应的地址
	 */
	@Test
	public void findByName() {
		
		Criteria cri = session.createCriteria(Address.class);//创建对应标准Criteria
		cri.createAlias("user", "u");//给Address中的属性对象user起别名
		
		cri.add(Restrictions.eq("u.name", "周天灵"));//标准之上添加约束，规则
		
		List<Address> addrList = cri.list();
		
		for(Address addr : addrList) {
			System.out.println(addr);
		}
		
	}
	
	
}
