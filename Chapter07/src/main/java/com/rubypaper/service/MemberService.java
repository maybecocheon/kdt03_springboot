package com.rubypaper.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Member;
import com.rubypaper.domain.Role;
import com.rubypaper.persistence.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	// member 이미 존재하는 경우 처리 추가 필요
	public boolean save(Member member) {
		Optional<Member> findMember = memberRepo.findById(member.getId());
		
		if (findMember.isPresent()) {
			return false;
		} else {
			member.setPassword(encoder.encode(member.getPassword()));
			member.setRole(Role.ROLE_MEMBER);
			member.setEnabled(true);
			memberRepo.save(member);
			
			return true;
		}
	}
}
