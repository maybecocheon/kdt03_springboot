package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PreDestroy;

@Repository
public class LogDao {
	
	public Connection con;
	public Statement stmt;
	public PreparedStatement pstmt;
	public ResultSet rs;

	public LogDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bootmission", "musthave", "tiger");
		} catch (Exception e) {
			System.out.println("DB 연결 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// LogDao 통해서 log 테이블에 값 넣을 메서드
	public void addLog(Map<String, Object> map) {
		String sql = "INSERT INTO dblog(method, sqlstring, success) VALUES (?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, (String)map.get("method"));
			pstmt.setString(2, (String)map.get("sqlstring"));
			pstmt.setInt(3, (Integer)map.get("success"));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("log 입력 중 오류 : " + e.getMessage());
			e.printStackTrace();;
		}
	}
	
	@PreDestroy
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
			System.out.println("LoaDao 자원 정상 해제됨!");
		} catch (SQLException e) {
			System.out.println("자원 해제 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
