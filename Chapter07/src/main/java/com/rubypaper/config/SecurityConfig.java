package com.rubypaper.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(security -> security			// ** => 그 아래에 있는 모든 폴더와 파일을 포함한다는 뜻
				.requestMatchers("/member/**").authenticated()	// authenticated() => 로그인해야 접근 가능
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")	// 로그인 기반) 둘 중 어느 하나 ROLE이면 접근 가능
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());		// 위 세 요청 외 모든 요청은 제한 없이 접근 가능
		
		// 사이트 간 요청 위조 막기
		http.csrf(cf -> cf.disable());
		
		// 로그인 페이지 연결
		http.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/loginSuccess", true));
		
		// 403 예외 처리
		http.exceptionHandling(exception -> exception.accessDeniedPage("/accessDenined"));
		
		// 로그아웃 설정 => 로그아웃 필터는 시큐리티가 기본으로 생성해 주기 때문에 controller에 "/logout" url 매핑하지 않아도 작동함
		http.logout(logout -> logout
			// 현재 브라우저와 연결된 세션 강제 종료(삭제)
			.invalidateHttpSession(true)
			// 세션 아이디가 저장된 쿠키 삭제
			.deleteCookies("JSESSIONID")
			// 로그아웃 후 이동할 URL 지정
			.logoutSuccessUrl("/login"));
		
		return http.build();
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired // AuthenticationManagerBuilder auth 주입
	public void authenticate(AuthenticationManagerBuilder auth) throws Exception {
		// {noop} => 암호화 안 한다는 뜻
//		auth.inMemoryAuthentication().withUser("member").password("{noop}abcd").roles("MEMBER");
//		auth.inMemoryAuthentication().withUser("manager").password("{noop}abcd").roles("MANAGER");
//		auth.inMemoryAuthentication().withUser("admin").password("{noop}abcd").roles("ADMIN");
		
		// jdbc 연동 시큐리티 => 잘 안 쓰임
		String query1 = "SELECT id username, concat('{noop}', password) password, true enabled FROM member WHERE id = ?";
		String query2 = "SELECT id, role FROM member WHERE id = ?";
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(query1)
			.authoritiesByUsernameQuery(query2);
	}
	
	// 비밀번호 암호화 객체 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
