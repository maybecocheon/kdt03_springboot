package edu.pnu.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepo;
	
	// 검색(Read - select All)
	public List<Member> findAll() {
		List<Member> list = memberRepo.findAll();
		System.out.println("검색 결과");
		for (Member m : list) {
			System.out.println(m);
		}
		return list;
	}
	
	// 검색(Read - select One)
	public Member findById(Integer id) {
		Member member = memberRepo.findById(id).get();
		System.out.println("검색 결과");
		System.out.println(member);
		return member;
	}
	
	// 입력(Create - insert)
	public Member save(Member member) {
		member.setRegidate(new Date());
		member = memberRepo.save(member);
		System.out.println("검색 결과");
		System.out.println(member);
		return member;
	}
	
	// 수정(Update - 객체 교체)
	public Member putMember(Integer id, Member member) {
		Member oldMember = memberRepo.findById(id).get();
		member.setId(id);
		member.setRegidate(oldMember.getRegidate());
		member = memberRepo.save(member);
		System.out.println("검색 결과");
		System.out.println(member);
		return member;
	}
	// 수정(Update - 일부 정보 수정)
	
	// 삭제(Delete - delete)
}
