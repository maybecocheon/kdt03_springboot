package edu.pnu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;

@RestController
@RequestMapping("/api")
public class MemberController {
	
	// 데이터 저장용 컬렉션 객체 생성
	private List<MemberDTO> list = new ArrayList<>();
	
	public MemberController() { // 데이터 초기화
		for (int i = 1; i <= 10; i++) {
			list.add(MemberDTO.builder()
						.id(i)
						.name("name" + i)
						.pass("pass" + i)
						.regidate(new Date())
						.build());
		}
	}
	
	// 검색 (Read - select All)
	@GetMapping("/member")
	public List<MemberDTO> getAllMember() {
		return list;
	}
	
	// 검색(Read - select One)
	@GetMapping("/member/{id}") // id 변수에 인수를 받음
	public MemberDTO getMemberById(@PathVariable Integer id) {
		for (MemberDTO dto : list) {
			if (dto.getId() == id) {
				return dto;
			}
		}
		return null;
	}
	
	// 입력(Create - insert)
	@PostMapping("/member")
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
	@PutMapping("/member/{id}")
	public MemberDTO putMember(@PathVariable Integer id, MemberDTO memberDTO){
		memberDTO.setId(id);
		for(int i = 0 ; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				list.set(i, memberDTO);
			}
		}
		return memberDTO;
	}
	
	// 수정(Update - 일부 정보 수정)
	@PatchMapping("/member/{id}")
	public MemberDTO patchMember(@PathVariable Integer id, MemberDTO memberDTO){
		// memberDTO의 id를 파라미터 id로 설정
		memberDTO.setId(id);
		for (MemberDTO dto : list) {
			if (dto.getId() == id) {
				dto.setName(memberDTO.getName() != null ? memberDTO.getName() : dto.getName());
				dto.setPass(memberDTO.getPass() != null ? memberDTO.getPass() : dto.getPass());
				dto.setRegidate(memberDTO.getRegidate() != null ? memberDTO.getRegidate() : dto.getRegidate());
			}
		}
		return memberDTO;
	}
	
	// 삭제(Delete - delete)
	@DeleteMapping("/member/{id}")
	public void deleteMember(@PathVariable Integer id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				list.remove(i);
			}
		}
	}
}
