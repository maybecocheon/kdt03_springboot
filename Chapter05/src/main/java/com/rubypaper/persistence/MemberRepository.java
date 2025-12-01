package com.rubypaper.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rubypaper.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	@Query("SELECT m FROM Member m JOIN FETCH m.boardList")
	List<Member> getMembers();
	
	@EntityGraph(value = "Member.boardList", type = EntityGraphType.LOAD)
	@Query("SELECT m FROM Member m")
	List<Member> getMembersEG();
}
