package edu.pnu;

import java.util.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Board;
import edu.pnu.domain.Member;
import edu.pnu.persistence.BoardRepository;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSetup implements ApplicationRunner {
	private final BoardRepository boardRepo;
	private final MemberRepository memberRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member m1 = Member.builder()
							.username("member").password("abcd")
							.alias("홍길동").enabled(true)
							.role("ROLE_MEMBER").build();
		memberRepo.save(m1);
		
		Member m2 = Member.builder()
							.username("manager").password("abcd")
							.alias("홍이동").enabled(true)
							.role("ROLE_MANAGER").build();
		memberRepo.save(m2);
		
		for (int i = 1; i <= 10; i++) {
			boardRepo.save(Board.builder()
								.title("title" + i)
								.content("content" + i)
								.createDate(new Date())
								.member((i % 2) == 1 ? m1: m2)
								.build());
		}
	}
}
