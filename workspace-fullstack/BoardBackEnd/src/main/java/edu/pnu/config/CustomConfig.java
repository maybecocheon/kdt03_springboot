package edu.pnu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(@NonNull CorsRegistry registry) {
		registry.addMapping("/**") 							// 모든 요청 URL에 대해 허용
				.allowCredentials(true) 					// 자바스크립트가 cross-origin 쿠키를 포함하도록 허용. 이 설정이 없으면
															// 요청에 localhost:8080의 쿠키가 포함되지 못함. 서버에서 쿠키를 이용하는
															// 핸들러가 없다면 생략해도 문제 없음.
				.allowedHeaders(HttpHeaders.AUTHORIZATION)	// 자바스크립트로 요청 시 포함시킬 수 있는 헤더 지정. 이 설정없이
															// "Authorization" 헤더를 자바스크립트로 포함하면 CORS 정책에 따라 요청 거부됨
				.exposedHeaders(HttpHeaders.AUTHORIZATION)	// 자바스크립트가 읽을 수 있는 헤더 지정. 이 설정이 없으면 자바스크립트로
															// 해당 헤더의 값을 읽을 수 없게 됨.
				.allowedMethods(HttpMethod.GET.name(),		// 허용 Method 목록 지정
								HttpMethod.POST.name())
				.allowedOrigins("http://localhost:3000",	// 허용 origin 목록 지정
								"http://127.0.0.1:3000");
	}
}
