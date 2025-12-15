package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepo;
	
	public List<Member> getMembers() {
		return memberRepo.findAll();
	}
	
	public Member getMember(String id) {
		return memberRepo.findById(id).get();
	}
}
