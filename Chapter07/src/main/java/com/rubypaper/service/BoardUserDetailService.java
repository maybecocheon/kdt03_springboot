package com.rubypaper.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rubypaper.config.SecurityUser;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

public class BoardUserDetailService implements UserDetailsService {
	// 해당 인터페이스를 구현하면 자동 생성 암호 나타나지 않음
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// MemberRepository로 회원 정보 조회하여 UserDetails 타입의 객체로 리턴함
		// User는 UserDetails의 자식 클래스
		
		Optional<Member> optional = memberRepo.findById(username);
		if (!optional.isPresent()) {
			throw new UsernameNotFoundException(username + " 사용자 없음");
		} else {
			Member member = optional.get();
			return new SecurityUser(member);
		}
	}
}
