package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.pnu.domain.MemberDTO;
import jakarta.annotation.PreDestroy;

@Repository
public class MemberDAO {

	public Connection con;
	public Statement stmt;
	public PreparedStatement pstmt;
	public ResultSet rs;
	
	public MemberDAO() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bootmission", "musthave", "tiger");
		} catch (Exception e) {
			System.out.println("DB 연결 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 검색 (Read - select All)
	public Map<String, Object> getAllMember() {
		// 검색한 것들 저장할 리스트 생성
		List<MemberDTO> list = new ArrayList<>();

		// 로그 저장할 맵 생성
		Map<String, Object> map = new HashMap<>();

		// sql문
		String sql = "SELECT * FROM member";

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();

				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("regidate"));

				list.add(dto);
			}
			map.put("sqlstring", sql);
			map.put("success", 1);
		} catch (SQLException e) {
			System.out.println("select All 중 오류 : " + e.getMessage());
			e.printStackTrace();
			map.put("success", 0);
		}
		map.put("method", "get");
		map.put("list", list);

		return map;
	}

	// 검색(Read - select One)
	public Map<String, Object> getMemberById(Integer id) {
		// 검색 결과 저장할 dto 생성
		MemberDTO dto = new MemberDTO();

		// 로그 저장할 맵 생성
		Map<String, Object> map = new HashMap<>();

		String sql = "SELECT * FROM member WHERE id = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setPass(rs.getString("pass"));
				dto.setRegidate(rs.getDate("regidate"));
			}
			
			String[] sqlstring = pstmt.toString().split(": ");
			map.put("sqlstring", sqlstring[1]);
			map.put("success", 1);
		} catch (SQLException e) {
			System.out.println("select 중 오류 : " + e.getMessage());
			e.printStackTrace();
			map.put("success", 0);
		}
		
		map.put("method", "get");
		map.put("dto", dto);

		return map;
	}

	// 입력(Create - insert)
	public Map<String, Object> postMember(MemberDTO memberDTO) {
		// 결과 저장할 dto 생성
		MemberDTO dto = null;

		// 로그 저장할 맵 생성
		Map<String, Object> map = new HashMap<>();

		String sql = "INSERT INTO member(pass, name) VALUES (?, ?)";

		try {
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, memberDTO.getPass());
			pstmt.setString(2, memberDTO.getName());
			
			String[] sqlstring = pstmt.toString().split(": ");
			map.put("sqlstring", sqlstring[1]);
			
			if (pstmt.executeUpdate() == 1) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dto = (MemberDTO)getMemberById(id).get("dto");
				}
			}
			map.put("success", 1);
		} catch (SQLException e) {
			System.out.println("insert 중 오류 : " + e.getMessage());
			e.printStackTrace();
			map.put("success", 0);
		}
		
		map.put("method", "post");
		map.put("dto", dto);

		return map;
	}

	// 수정(Update - 객체 교체)
	public Map<String, Object> putMember(Integer id, MemberDTO memberDTO) {
		// 결과 저장할 dto 생성
		MemberDTO dto = null;

		// 로그 저장할 맵 생성
		Map<String, Object> map = new HashMap<>();

		String sql = "UPDATE member SET pass = ?, name = ?, regidate = curdate() WHERE id = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getPass());
			pstmt.setString(2, memberDTO.getName());
			pstmt.setInt(3, id);
			
			String[] sqlstring = pstmt.toString().split(": ");
			map.put("sqlstring", sqlstring[1]);
			if (pstmt.executeUpdate() == 1) {
				dto = (MemberDTO)getMemberById(id).get("dto");
			}
			map.put("success", 1);
		} catch (SQLException e) {
			System.out.println("update(put) 중 오류 : " + e.getMessage());
			e.printStackTrace();
			map.put("success", 0);
		}
		
		map.put("method", "put");
		map.put("dto", dto);

		return map;
	}

	// 수정(Update - 일부 정보 수정)
	public Map<String, Object> patchMember(Integer id, MemberDTO memberDTO) {
		// 결과 저장할 dto 생성
		MemberDTO dto = null;

		// 로그 저장할 맵 생성
		Map<String, Object> map = new HashMap<>();

		// id로 찾은 dto 불러 오기
		MemberDTO old = (MemberDTO) getMemberById(id).get("dto");

		String sql = "UPDATE member SET pass = ?, name = ? WHERE id = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getPass() != null ? memberDTO.getPass() : old.getPass());
			pstmt.setString(2, memberDTO.getName() != null ? memberDTO.getName() : old.getName());
			pstmt.setInt(3, id);
			
			String[] sqlstring = pstmt.toString().split(": ");
			map.put("sqlstring", sqlstring[1]);
			if (pstmt.executeUpdate() == 1) {
				dto = (MemberDTO)getMemberById(id).get("dto");
			}
			map.put("success", 1);
		} catch (SQLException e) {
			System.out.println("update(patch) 중 오류 : " + e.getMessage());
			e.printStackTrace();
			map.put("success", 0);
		}

		map.put("method", "patch");
		map.put("dto", dto);

		return map;
	}

	// 삭제(Delete - delete)
	public Map<String, Object> deleteMember(Integer id) {
		String sql = "DELETE FROM member WHERE id = ?";

		// 로그 저장할 맵 생성
		Map<String, Object> map = new HashMap<>();

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
			
			String[] sqlstring = pstmt.toString().split(": ");
			map.put("sqlstring", sqlstring[1]);
			map.put("success", 1);
		} catch (SQLException e) {
			System.out.println("delete 중 오류 : " + e.getMessage());
			e.printStackTrace();
			map.put("success", 0);
		}
		
		map.put("method", "delete");
		
		return map;
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
			System.out.println("MemberDAO 자원 정상 해제됨!");
		} catch (SQLException e) {
			System.out.println("자원 해제 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
