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
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor					// 한 번만 실행되는 필터
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private final MemberRepository memberRepo;
	
	@Override
	// 필터 커스텀 할 수 있는 메서드
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
		
		// 토큰에서 username, provider, email 추출
		// provider와 email은 OAuth2USer가 아니면 null
		String username = JWTUtil.getClaim(jwtToken, JWTUtil.usernameClaim);
		String provider = JWTUtil.getClaim(jwtToken, JWTUtil.providerClaim);
		String email = JWTUtil.getClaim(jwtToken, JWTUtil.emailClaim);

		User user = null;
		Optional<Member> opt = memberRepo.findById(username);
		// 토큰에서 얻은 username으로 DB 검색했는데 사용자 부재하면 필터 그냥 통과
		if (!opt.isPresent()) {
			// DB에 없고 provider, email이 null이면
			if (provider == null || email == null) {
				System.out.println("[JWTAuthorizationFilter]not found user!");
				filterChain.doFilter(request, response);
				return;
			}
			// DB에 사용자를 저장하지 않는 OAuth2 인증인 경우
			System.out.println("[JWTAuthorizationFilter]username: " + username);
			user = new User(username, "****", AuthorityUtils.createAuthorityList(Role.ROLE_MEMBER.toString()));
		}
		
		// 인증 객체 생성 : 사용자명과 권한 관리를 위한 정보를 입력(암호 필요 없음)
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// SecurityContext에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		// SecurityFilterChain의 다음 필터로 이동
		filterChain.doFilter(request, response);
	}
}
