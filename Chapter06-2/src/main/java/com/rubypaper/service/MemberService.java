package com.rubypaper.service;

import com.rubypaper.domain.Member;

public interface MemberService {
	Member getMember(Member member);
	Member getMember(String id);
}
