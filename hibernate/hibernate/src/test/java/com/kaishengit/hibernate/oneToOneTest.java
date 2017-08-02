package com.kaishengit.hibernate;

import org.junit.Test;

import com.kaishengit.pojo.Book;
import com.kaishengit.pojo.Content;

/**
 * oneToOne���ԣ����ֶΣ�
 * �������鼮����������鼮����
 * @author SPL
 *
 */
public class oneToOneTest extends BaseTest{
	
	/**
	 * �鿴�鼮���ӳټ����鼮����
	 */
	@Test
	public void bookLoadContent() {
		
	}
	/**
	 * ����鼮
	 */
	@Test
	public void saveBook() {
		
		Book book = new Book();
		book.setTittle("�ʱ���");
		
		session.save(book);//1.����book����ȡ����ֵ
		
		Content content = new Content();
		content.setContent("ewifwesafhewfhuasefghusdgfcasd");
		content.setBook(book);
		
		book.setContent(content);
		
		session.save(content);
	}
	
	/**
	 * ����book_id�鿴����
	 */
	@Test
	public void findByBookId() {
		
		Book book = (Book) session.get(Book.class, 1);
		System.out.println(book.getTittle());
		
		//�ӳټ���
		String content = book.getContent().getContent();
		System.out.println(content);
	}
	
}
