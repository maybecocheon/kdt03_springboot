package edu.pnu.service;
import java.util.List;


import edu.pnu.dao.MemberDAO;
import edu.pnu.domain.MemberDTO;

public class MemberService {
	
	// DB 접근 객체 생성
	MemberDAO dao = new MemberDAO();
	
	// 검색 (Read - select All)
	public List<MemberDTO> getAllMember() {
		return dao.getAllMember();
	}
	
	// 검색(Read - select One)
	public MemberDTO getMemberById(Integer id) {
		return dao.getMemberById(id);
	}
	
	// 입력(Create - insert)
	public MemberDTO postMember(MemberDTO memberDTO) {
		return dao.postMember(memberDTO);
	}
	
	// 수정(Update - 객체 교체)
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		return dao.putMember(id, memberDTO);
	}
	
	// 수정(Update - 일부 정보 수정)
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		return dao.patchMember(id, memberDTO);
	}
	
	// 삭제(Delete - delete)
	public void deleteMember(Integer id) {
		dao.deleteMember(id);
	}
}
