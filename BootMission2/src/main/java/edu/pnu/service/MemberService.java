package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pnu.domain.MemberDTO;

public class MemberService {

	// 데이터 저장용 컬렉션 객체 생성
	private List<MemberDTO> list = new ArrayList<>();

	public MemberService() { // 데이터 초기화
		for (int i = 1; i <= 10; i++) {
			list.add(MemberDTO.builder().id(i).name("name" + i).pass("pass" + i).regidate(new Date()).build());
		}
	}

	// 검색 (Read - select All)
	public List<MemberDTO> getAllMember() {
		return list;
	}

	// 검색(Read - select One)
	public MemberDTO getMemberById(Integer id) {
		for (MemberDTO dto : list) {
			if (dto.getId() == id) {
				return dto;
			}
		}
		return null;
	}

	// 입력(Create - insert)
	public MemberDTO postMember(MemberDTO memberDTO) {
		// id는 새로 생성, regidate는 현재 시간 날짜로 수정
		Integer maxId = 0;
		for (MemberDTO dto : list) {
			if (maxId < dto.getId()) {
				maxId = dto.getId();
			}
		}
		memberDTO.setId(maxId + 1);
		memberDTO.setRegidate(new Date());
		list.add(memberDTO);
		return memberDTO;
	}

	// 수정(Update - 객체 교체)
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		memberDTO.setId(id);
		memberDTO.setRegidate(new Date());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				list.set(i, memberDTO);
			}
		}
		return memberDTO;
	}

	// 수정(Update - 일부 정보 수정)
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		memberDTO.setId(id);
		for (MemberDTO dto : list) {
			if (dto.getId() == id) {
				if (memberDTO.getName() != null)
					dto.setName(memberDTO.getName());
				if (memberDTO.getPass() != null)
					dto.setPass(memberDTO.getPass());

				return dto;
			}
		}
		return null;
	}

	// 삭제(Delete - delete)
	public void deleteMember(Integer id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				list.remove(i);
			}
		}
	}
}
