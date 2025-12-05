package com.rubypaper.config.filter;

import java.io.IOException;

import com.rubypaper.domain.Member;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request; 
		String uri = req.getRequestURI();
		
		// localhost:8080, localhost:8080/login, localhost:8080/getBoardList => 인가 필요없이 실행
		if (uri.equals("/") || uri.equals("/login") || uri.startsWith("/getBoardList")) {
			chain.doFilter(request, response);
			return;
		}

		// 그외 URL은 인증이 필요. 인증이 되어 있지 않으면 로그인 화면으로 이동
		HttpSession session = req.getSession();
		Member member = (Member)session.getAttribute("member");
		HttpServletResponse resp = (HttpServletResponse)response;
		if (member == null) {
			resp.sendRedirect("/login");
			return;
		}
		// admin 계정이 아닌데 게시글을 삭제하려고 하면 /getBoardList로 이동
		if (uri.equals("/deleteBoard") && !member.getRole().equals("ROLE_ADMIN")) {
			resp.sendRedirect("/login");
			return;
		}
		
		chain.doFilter(request, response);
	}
}

