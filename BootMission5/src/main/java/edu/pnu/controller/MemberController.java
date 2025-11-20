package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;
import edu.pnu.service.MemberService;

@RestController		// 객체 자동 생성
@RequestMapping("/api")
public class MemberController {
	
	private MemberService memberService;
	
	// 의존성 주입
	@Autowired		// 생성자 하나만 있으면 생략 가능
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	// 검색 (Read - select All)
	@GetMapping("/member")
	public List<MemberDTO> getAllMember() {
		return memberService.getAllMember();
	}
	
	// 검색(Read - select One)
	@GetMapping("/member/{id}") // id 변수에 인수를 받음
	public MemberDTO getMemberById(@PathVariable Integer id) {
		return memberService.getMemberById(id);
	}
	
	// 입력(Create - insert)
	@PostMapping("/member")
	public MemberDTO postMember(@RequestBody MemberDTO memberDTO) {
		return memberService.postMember(memberDTO);
	}
	
	// 수정(Update - 객체 교체)
	@PutMapping("/member/{id}")
	public MemberDTO putMember(@PathVariable Integer id, @RequestBody MemberDTO memberDTO){
		return memberService.putMember(id, memberDTO);
	}
	
	// 수정(Update - 일부 정보 수정)
	@PatchMapping("/member/{id}")
	public MemberDTO patchMember(@PathVariable Integer id, @RequestBody MemberDTO memberDTO){
		return memberService.patchMember(id, memberDTO);
	}
	
	// 삭제(Delete - delete)
	@DeleteMapping("/member/{id}")
	public void deleteMember(@PathVariable Integer id) {
		memberService.deleteMember(id);
	}
}
