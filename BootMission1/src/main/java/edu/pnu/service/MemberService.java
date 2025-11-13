package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pnu.domain.MemberDTO;

public class MemberService {
	
	List<MemberDTO> list = new ArrayList<>();
	
	public MemberService() {
		for (int i = 1; i <= 10; i++) {
			list.add(MemberDTO.builder()
							.id(i)
							.name("name " + i)
							.pass("pass " + i)
							.regidate(new Date())
							.build());
		}
	}
}
