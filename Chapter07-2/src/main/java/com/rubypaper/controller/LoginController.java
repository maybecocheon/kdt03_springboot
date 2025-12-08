package com.rubypaper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rubypaper.domain.Member;

@Controller
public class LoginController {

	@GetMapping("/login")
	public void login() {
		System.out.println("login 요청");
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		System.out.println("loginSuccess 요청");
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
		System.out.println("accessDenied");
	}
	
	// 로그인 정보 확인 -> 일부 주입
	@GetMapping("/auth1")
	@ResponseBody
	public ResponseEntity<?> auth1(@AuthenticationPrincipal User user){
		if (user == null) {
			return ResponseEntity.ok("로그인 상태가 아닙니다.");
		}
		return ResponseEntity.ok(user);
	}
	
	// 로그인 정보 확인 -> 전체 주입
	@GetMapping("/auth2")
	public @ResponseBody ResponseEntity<?> auth2(Authentication auth){
		System.out.println(auth.getPrincipal());
		System.out.println(auth.getCredentials());
		System.out.println(auth.getAuthorities());
		
		return ResponseEntity.status(HttpStatus.OK).body(auth);
	}
}
