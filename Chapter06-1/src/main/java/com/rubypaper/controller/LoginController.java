package com.rubypaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.rubypaper.domain.Member;
import com.rubypaper.service.MemberService;

@SessionAttributes("member")
// "member"라는 이름으로 세션에 속성을 추가, 이후 Model에 같은 이름의 속성으로 객체가 추가되면 자동으로 세션에 저장
@Controller
public class LoginController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login")
	public void loginView() {
	}
	
	@PostMapping("/login")
	public String login(Member member, Model model) {
		Member findMember = memberService.getMember(member);
		// 로그인 성공 시
		if (findMember != null && findMember.getPassword().equals(member.getPassword())) {
			model.addAttribute("member", findMember);	// 세션에 "member" 속성 존재하므로 세션에 findMember 저장
			return "redirect:getBoardList";
		}
		return "redirect:login";
	}
	
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete(); 	// 세션 속성들 중 @SessionAttribute로 지정한 속성들을 삭제
		return "redirect:index.html";
	}
}
