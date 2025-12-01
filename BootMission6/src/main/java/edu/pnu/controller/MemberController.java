package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public List<Member> findAll() {
		return memberService.findAll();
	}
	
	// 검색(Read - select One)
	@GetMapping("/member/{id}")
	public Member findById(@PathVariable Integer id) {
		return memberService.findById(id);
	}
	
	// 입력(Create - insert)
	@PostMapping("/member")
	public Member save(@RequestBody Member member) {
		return memberService.save(member);
	}
	
	// 수정(Update - 객체 교체)
	
	// 수정(Update - 일부 정보 수정)
	
	// 삭제(Delete - delete)
}
