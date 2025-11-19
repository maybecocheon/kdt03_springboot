package edu.pnu.service;
import java.util.List;
import java.util.Map;

import edu.pnu.dao.LogDao;
import edu.pnu.dao.MemberDAO;
import edu.pnu.domain.MemberDTO;

@SuppressWarnings("unchecked")
public class MemberService {
	
	// DB 접근 객체 생성
	MemberDAO dao = new MemberDAO();
	LogDao log = new LogDao();
	
	// 검색 (Read - select All)
	public List<MemberDTO> getAllMember() {
		Map<String, Object> map = dao.getAllMember();
		log.addLog(map);
		return (List<MemberDTO>)map.get("list");
	}
	
	// 검색(Read - select One)
	public MemberDTO getMemberById(Integer id) {
		Map<String, Object> map = dao.getMemberById(id);
		log.addLog(map);
		return (MemberDTO)map.get("dto");
	}
	
	// 입력(Create - insert)
	public MemberDTO postMember(MemberDTO memberDTO) {
		Map<String, Object> map = dao.postMember(memberDTO);
		log.addLog(map);
		return (MemberDTO)map.get("dto");
	}
	
	// 수정(Update - 객체 교체)
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> map = dao.putMember(id, memberDTO);
		log.addLog(map);
		return (MemberDTO)map.get("dto");
	}
	
	// 수정(Update - 일부 정보 수정)
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> map = dao.patchMember(id, memberDTO);
		log.addLog(map);
		return (MemberDTO)map.get("dto");
	}
	
	// 삭제(Delete - delete)
	public void deleteMember(Integer id) {
		Map<String, Object> map = dao.deleteMember(id);
		log.addLog(map);
	}
}
