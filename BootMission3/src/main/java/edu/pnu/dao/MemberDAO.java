package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	public List<MemberDTO> getAllMember() {
		// 검색한 것들 저장할 리스트 생성
		List<MemberDTO> list = new ArrayList<>();

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
		} catch (SQLException e) {
			System.out.println("select All 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	// 검색(Read - select One)
	public MemberDTO getMemberById(Integer id) {
		// 검색 결과 저장할 dto 생성
		MemberDTO dto = new MemberDTO();

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
		} catch (SQLException e) {
			System.out.println("select 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}
		return dto;
	}

	// 입력(Create - insert)
	public MemberDTO postMember(MemberDTO memberDTO) {
		// 결과 저장할 dto 생성
		MemberDTO dto = null;

		String sql = "INSERT INTO member(pass, name) VALUES (?, ?)";

		try {
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, memberDTO.getPass());
			pstmt.setString(2, memberDTO.getName());
			if (pstmt.executeUpdate() == 1) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					dto = getMemberById(id);
				}
			}
		} catch (SQLException e) {
			System.out.println("insert 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}

		return dto;
	}

	// 수정(Update - 객체 교체)
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		// 결과 저장할 dto 생성
		MemberDTO dto = null;
		
		String sql = "UPDATE member SET pass = ?, name = ?, regidate = curdate() WHERE id = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getPass());
			pstmt.setString(2, memberDTO.getName());
			pstmt.setInt(3, id);
			if (pstmt.executeUpdate() == 1) {
				dto = getMemberById(id);
			}
		} catch (SQLException e) {
			System.out.println("update(put) 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}

		return dto;
	}

	// 수정(Update - 일부 정보 수정)
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		// 결과 저장할 dto 생성
		MemberDTO dto = null;
		
		// id로 찾은 dto 불러 오기
		MemberDTO old = getMemberById(id);
		
		String sql = "UPDATE member SET pass = ?, name = ? WHERE id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberDTO.getPass() != null ? memberDTO.getPass() : old.getPass());
			pstmt.setString(2, memberDTO.getName() != null ? memberDTO.getName() : old.getName());
			pstmt.setInt(3, id);
			if (pstmt.executeUpdate() == 1) {
				dto = getMemberById(id);
			}
		} catch (SQLException e) {
			System.out.println("update(patch) 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}

		return dto;
	}

	// 삭제(Delete - delete)
	public void deleteMember(Integer id) {
		String sql = "DELETE FROM member WHERE id = ?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("delete 중 오류 : " + e.getMessage());
			e.printStackTrace();
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
			System.out.println("DAO 자원 정상 해제됨!");
		} catch (SQLException e) {
			System.out.println("자원 해제 중 오류 : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
