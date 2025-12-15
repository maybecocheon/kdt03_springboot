package edu.pnu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping("/member")
	public ResponseEntity<?> getMembers(String id) {
		Map<String, Object> ret = new HashMap<>();
		ret.put("key", "member");
		
		if (id == null) {
			ret.put("jsondata", memberService.getMembers());
		} else {
			ret.put("jsondata", memberService.getMember(id));
		}
		return ResponseEntity.ok(ret);
	}
}
