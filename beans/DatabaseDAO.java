package com.beans;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class DatabaseDAO {

	public Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/data?characterEncoding=utf8", "root", "");
	}
	
	public int executeUpdateSQL(String sql,Object... args){
		PreparedStatement pstmt=null;
		Connection conn=null;
		int counts=0;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			for(int i=1;i<=args.length;i++){
				pstmt.setObject(i, args[i-1]);
			}
			counts=pstmt.executeUpdate();
			return counts;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			close(pstmt);
			close(conn);
		}
		return 0;
	}
	
	public ResultSet executeSelectSQL(String sql,Object... args){
		PreparedStatement pstmt=null;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			for(int i=1;i<=args.length;i++){
				pstmt.setObject(i, args[i-1]);
			}
			rs=pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
//			close(conn,pstmt,rs);
		}
		return null;
	}
	
	public ResultSet executeSelectSQL(String sql){
		PreparedStatement pstmt=null;
		Connection conn=null;
		ResultSet rs=null;
		try {
			conn=getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
//			close(conn,pstmt,rs);
		}
		return null;
	}
	
	public void close(Connection cn){
		if (cn != null) {
			try {
				if (! cn.getAutoCommit()) {
					cn.setAutoCommit(true);
				}
				cn.close();
			} catch (SQLException e) {
			}
		}
	}
	public void close(PreparedStatement pstmt){
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}
	public void close(ResultSet rs){
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}
	public void close(Connection cn, PreparedStatement pstmt, ResultSet rs){
		this.close(rs);
		this.close(pstmt);
		this.close(cn);
	}
	
	public void rollback(Connection cn){
		if (cn != null) {
			try {
				cn.rollback();
			} catch (SQLException e) {
			}
		}
	}
}
