package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.pnu.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

}
