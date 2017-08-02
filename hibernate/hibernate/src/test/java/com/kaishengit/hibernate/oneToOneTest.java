package com.kaishengit.hibernate;

import org.junit.Test;

import com.kaishengit.pojo.Book;
import com.kaishengit.pojo.Content;

/**
 * oneToOne测试（大字段）
 * 场景：书籍名点击加载书籍内容
 * @author SPL
 *
 */
public class oneToOneTest extends BaseTest{
	
	/**
	 * 查看书籍，延迟加载书籍内容
	 */
	@Test
	public void bookLoadContent() {
		
	}
	/**
	 * 添加书籍
	 */
	@Test
	public void saveBook() {
		
		Book book = new Book();
		book.setTittle("资本论");
		
		session.save(book);//1.保存book，获取主键值
		
		Content content = new Content();
		content.setContent("ewifwesafhewfhuasefghusdgfcasd");
		content.setBook(book);
		
		book.setContent(content);
		
		session.save(content);
	}
	
	/**
	 * 根据book_id查看数据
	 */
	@Test
	public void findByBookId() {
		
		Book book = (Book) session.get(Book.class, 1);
		System.out.println(book.getTittle());
		
		//延迟加载
		String content = book.getContent().getContent();
		System.out.println(content);
	}
	
}
