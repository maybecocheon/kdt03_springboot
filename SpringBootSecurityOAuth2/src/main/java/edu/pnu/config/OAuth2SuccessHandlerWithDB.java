package edu.pnu.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

//데이터베이스에 OAuth2 사용자 정보를 저장하고, JWT를 생성해서 응답 헤더에 추가하도록 작성된 컴포넌트
@Component("OAuth2SuccessHandlerWithDB")
@RequiredArgsConstructor
public class OAuth2SuccessHandlerWithDB extends OAuth2SuccessHandler {
	private final MemberRepository memRepo;
	private final PasswordEncoder encoder;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Map<String, String> map = getUserInfo(authentication);

		String username = map.get("provider") + "_" + map.get("email");

		memRepo.save(Member.builder().username(username).password(encoder.encode("1a2s3d4f")).role(Role.ROLE_MEMBER)
				.enabled(true).build());
		
		String token = JWTUtil.getJWT(username);

		sendJWTtoClient(response, token);
	}
}
