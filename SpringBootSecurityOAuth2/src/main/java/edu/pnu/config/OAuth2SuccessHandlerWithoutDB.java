package edu.pnu.config;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// OAuth2 사용자 정보로 JWT를 생성해서 응답 헤더에 추가하도록 작성된 컴포넌트
@Component("OAuth2SuccessHandlerWithoutDB")
public class OAuth2SuccessHandlerWithoutDB extends OAuth2SuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		Map<String, String> map = getUserInfo(authentication);
		
		String username = map.get("provider") + "_" + map.get("email");
		
		String token = JWTUtil.getJWT(username, map.get("provider"), map.get("email"));
		
		sendJWTtoClient(response, token);
	}
}
