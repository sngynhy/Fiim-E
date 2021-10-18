package model.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC {
	// JDBC : DB�� �����ϱ� ���� ����̹� �ε��ϴ� ����

	// Connection ��ü ���� (open)
	public static Connection getConnection() {
		Connection conn = null;
		
		String DName = "com.mysql.cj.jdbc.Driver";  // JDBC�� ����̹� Ŭ������
		
		String url = "jdbc:mysql://localhost:3306/ohdb";
		String user="root";
		String password="6829";
		
		try {
			Class.forName(DName);
			conn = DriverManager.getConnection(url, user, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
		
	}
	
	// ������ Connection ��ü�� close
	public static void close(Connection conn, PreparedStatement pstmt) {
		
		try {
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
