package com.rubypaper.config.filter;

import java.io.IOException;

import com.rubypaper.domain.Member;
import com.rubypaper.service.MemberService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginFilter implements Filter {

	private final MemberService memberService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		
		String uri = req.getRequestURI();	// URI
		String method = req.getMethod();	// Method
		
		// POST:/login 이 아니면 그냥 통과
		if (!uri.equals("/login") || !method.equalsIgnoreCase("post")) {
			chain.doFilter(request, response);
			return;
		}

		// 요청 정보에서 id, password 추출
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		
		// id, password 검증
		if (id == null || id.isEmpty() || password == null || password.isEmpty()) {
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("/login");
			return;
		}

		// DB에서 member 검색
		Member member = memberService.getMember(id);
		
		// password 일치 여부 검사. 일치하면 /getBoardList 화면으로 이동, 다르면 /login 화면으로 이동
		if (member != null && member.getPassword().equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("member", member);
			req.getRequestDispatcher("/getBoardList").forward(request, response);
		} else {
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("/login");
		}
	}
}

