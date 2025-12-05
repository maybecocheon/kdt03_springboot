package com.rubypaper.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rubypaper.config.filter.AuthFilter;
import com.rubypaper.config.filter.LoginFilter;
import com.rubypaper.service.MemberService;

@Configuration
public class RegisterFilterConfig {

	@Bean
	FilterRegistrationBean<LoginFilter> loginFilter(MemberService memberService) {
		FilterRegistrationBean<LoginFilter> reg = new FilterRegistrationBean<>();
		reg.setFilter(new LoginFilter(memberService));
		reg.addUrlPatterns("/login");
		return reg;
	}
	
	@Bean
	FilterRegistrationBean<AuthFilter> authFilter() {
		FilterRegistrationBean<AuthFilter> reg = new FilterRegistrationBean<>();
		reg.setFilter(new AuthFilter());
		reg.addUrlPatterns("/*");
		return reg;
	}
}

