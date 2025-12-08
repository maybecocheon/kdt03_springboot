package com.rubypaper.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardUserDetailService implements UserDetailsService {
	
	private final MemberRepository memberRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException(username + " 사용자 없음"));
		return new User(member.getId(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
	}
}
