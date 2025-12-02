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

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	// 검색 (Read - select All)
	@GetMapping("/member")
	public List<Member> findAllMember() {
		return memberService.findAllMember();
	}
	
	// 검색(Read - select One)
	@GetMapping("/member/{id}")
	public Member findByIdMember(@PathVariable Integer id) {
		return memberService.findByIdMember(id);
	}
	
	// 입력(Create - insert)
	@PostMapping("/member")
	public Member saveMember(@RequestBody Member member) {
		return memberService.saveMember(member);
	}
	
	// 수정(Update - 객체 교체)
	@PutMapping("/member/{id}")
	public Member putMember(@PathVariable Integer id, @RequestBody Member member) {
		return memberService.putMember(id, member);
	}
	
	// 수정(Update - 일부 정보 수정)
	@PatchMapping("/member/{id}")
	public Member patchMember(@PathVariable Integer id, @RequestBody Member member) {
		return memberService.patchMember(id, member);
	}
	
	// 삭제(Delete - delete)
	@DeleteMapping("/member/{id}")
	public Member deleteMember(@PathVariable Integer id) {
		return memberService.deleteMember(id);
	}
}
