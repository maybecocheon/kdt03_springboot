package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final MemberRepository memberRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 요청 헤더에서 JWT를 얻어 온다
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (jwtToken == null || !jwtToken.startsWith(JWTUtil.prefix)) {
			// 없거나 "Bearer "로 시작하지 않는다면
			// 그냥 다음 필터로 통과
			filterChain.doFilter(request, response);
			return;
		}
		
		// 토큰에서 username 추출
		String username = JWTUtil.getClaim(jwtToken, JWTUtil.usernameClaim);
		Optional<Member> opt = memberRepo.findById(username);
		// 토큰에서 얻은 username으로 DB 검색했는데 사용자 부재하면 필터 그냥 통과
		if (!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		Member findmember = opt.get();
		
		// UserDetails 타입 객체 생성
		User user = new User(findmember.getUsername(), findmember.getPassword(), AuthorityUtils.createAuthorityList(findmember.getRole().toString()));
		
		// 인증 객체 생성 : 사용자명과 권한 관리를 위한 정보를 입력(암호 필요 없음)
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// SecurityContext에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// SecurityFilterChain의 다음 필터로 이동
		filterChain.doFilter(request, response);
	}
}
