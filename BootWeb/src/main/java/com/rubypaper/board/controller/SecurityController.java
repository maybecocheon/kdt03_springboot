package com.rubypaper.board.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.rubypaper.util.JWTUtil;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SecurityController {

	@GetMapping("/system/login")
	public void login() {}
	
	@GetMapping("/system/accessDenied")
	public void accessDenied() {}
	
	@GetMapping("/system/logout")
	public void logout() {}
	
	@GetMapping("/admin/adminPage")
	public void admin() {}
	
	@PostMapping("/api/jwtcallback")
	public void apiCallback(@CookieValue String jwtToken, HttpServletResponse response) throws IOException {
		System.out.println("[SecurityController]jwtToken: " + jwtToken);
		response.setHeader(HttpHeaders.AUTHORIZATION, JWTUtil.prefix + jwtToken);
		response.sendRedirect("/board/getBoardList");
	}
}
