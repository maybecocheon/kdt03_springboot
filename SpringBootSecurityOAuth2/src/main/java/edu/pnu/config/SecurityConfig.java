package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.config.filter.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AuthenticationConfiguration authenticationConfiguration;
	private final MemberRepository memberRepo;
	
	@Resource(name="${project.oauth2login.qualifier.name}")
	private AuthenticationSuccessHandler oauth2SuccessHandler;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// csrf 보호 비활성화
		http.csrf(cf -> cf.disable());
		
		// 접근 권한 -> 인가
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());	// AuthorizationFilter 등록
		
		// Form을 이용한 로그인을 사용하지 않겠다는 설정
		// UsernamePasswordAuthenticationFilter 제거
		http.formLogin(form -> form.disable());
		
		// Http Basic인증 방식을 사용하지 않겠다는 설정 => 로그인 창 팝업 안 띄우는 것
		// Authentication Header에 저장된 id:pwd를 이용하는 인증 방식
		// BasicAuthenticationFilter 제거
		// 토큰 인증 방식에서는 사용할 수 없음
		http.httpBasic(basic -> basic.disable());
		
		// SessionManagementFilter에서 이 설정을 확인 => 세션 생성 안 하는 것
		// 개발자가 HttpSession을 요청하지 않는 한 생성하지 않는다.
		// (reqeust.getSession())
		http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		// 스프링 시큐리티의 필터체인에 작성한 필터를 추가한다. UsernamePasswordAuthenticationFilter를 상속한 필터이므로
		// 원래 UsernamePasswordAuthenticationFilter가 위치하는 곳에 대신 추가된다.
		http.addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()));
		
		// 스프링 시큐리티가 등록한 필터들 중에서 AuthorizationFilter 앞에 JWTAuthorizationFilter를 삽입한다.
		http.addFilterBefore(new JWTAuthorizationFilter(memberRepo), AuthorizationFilter.class);
		
		// OAuth2 로그인에 성공한 뒤 후처리를 진행하는 핸들러 등록
		// → JWT 토큰을 만들어서 응답 헤더에 설정
		// → 필요에 따라 로그인 사용자 DB에 저장
		http.oauth2Login(oauth2 -> oauth2.loginPage("/login").successHandler(oauth2SuccessHandler));
		
		// cors 등록
		http.cors(cors -> cors.configurationSource(corsSource()));
		
		return http.build();
	}

	private CorsConfigurationSource corsSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:3000");		// 요청을 허용할 서버
		config.addAllowedMethod(CorsConfiguration.ALL);			// 요청을 허용할 Method
		config.addAllowedHeader(CorsConfiguration.ALL);			// 요청을 허용할 Header
		config.setAllowCredentials(true);						// 요청/응답에 자격증명정보/쿠키 포함을 허용 여부
		config.addExposedHeader(HttpHeaders.AUTHORIZATION);		// 응답 헤더에 Authorization 추가
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);		// 위 설정을 적용할 Rest 서버의 URL 패턴 설정
		
		return source;
	}
}
