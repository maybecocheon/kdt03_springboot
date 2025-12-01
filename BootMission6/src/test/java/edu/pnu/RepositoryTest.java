package edu.pnu;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class RepositoryTest {
	
	@Autowired
	private MemberRepository memberRepo;
	
	//@Test
	public void insert() {
		for (int i = 1; i <= 5; i++) {
			Member member = new Member();
			member.setName("member" + i);
			member.setPass("pass" + i);
			member.setRegidate(new Date());
			memberRepo.save(member);
		}
	}
	
	@Test
	public void findAll() {
		List<Member> list = memberRepo.findAll();
		System.out.println("검색 결과");
		for (Member m : list) {
			System.out.println(m);
		}
	}
}
