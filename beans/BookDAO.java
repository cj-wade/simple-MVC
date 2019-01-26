package com.beans;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.ArrayList;

public class BookDAO {
	private DatabaseDAO db=new DatabaseDAO();
	public BookDAO() {
//		try {
//			if(context == null) {
//				context = new InitialContext();
//			}
//			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/sqlconnpool");
//		}catch(NamingException e){
//			System.out.println("找不到资源");
//		}
	}

	//根据书号查询图书信息
	public BookBean searchBook(String bookid) {
		ResultSet rst = null;
		BookBean book = new BookBean();
		try {
			String sql = "SELECT * FROM book WHERE bookid=?";
			rst = db.executeSelectSQL(sql,bookid);
			if(rst.next()) {
				book.setBookid(rst.getString("bookid"));
				book.setTitle(rst.getString("title"));
				book.setAuthor(rst.getString("author"));
				book.setPublisher(rst.getString("publisher"));
				book.setPrice(rst.getFloat("price"));
				System.out.println("----666----");
				System.out.println(book.getBookid());
				return book;
			}else {
				return null;
			}
		}catch(SQLException sqe) {
			return null;
		}finally {
			try {
				rst.close();
			}catch(SQLException sqe) {
				
			}
		}
	}
	public boolean insertBook(BookBean book) {

		   String sql = "INSERT INTO book VALUES(?,?,?,?,?)"; 
		   int count=db.executeUpdateSQL(sql,book.getBookid(),book.getTitle(),
				   book.getAuthor(),book.getPublisher(),book.getPrice());
				if(count>0){
					System.out.println("wzx成功了");
				}
			return true;

		}
	
}
