package com.rubypaper.board.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rubypaper.board.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
