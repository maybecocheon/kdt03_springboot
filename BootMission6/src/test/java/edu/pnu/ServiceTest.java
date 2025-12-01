package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.service.MemberService;

@SpringBootTest
public class ServiceTest {
	@Autowired
	private MemberService memberService;
	
	@Test
	public void findAll() {
		memberService.findAll();
	}
}
