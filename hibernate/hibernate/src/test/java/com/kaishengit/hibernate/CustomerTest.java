package com.kaishengit.hibernate;

import com.kaishengit.pojo.Customer;

public class CustomerTest extends BaseTest {
	
	@org.junit.Test
	public void save() {
		Customer customer = new Customer();
		customer.setName("ÎÞ¾¡");
        session.save(customer);
		
	}
	
	
	
}
