package com.rubypaper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rubypaper.board.domain.Board;
import com.rubypaper.board.domain.Member;
import com.rubypaper.board.domain.Role;
import com.rubypaper.board.persistence.BoardRepository;
import com.rubypaper.board.persistence.MemberRepository;

@SpringBootTest
public class BoardRepositoryTest {
	@Autowired
	private MemberRepository memRepo;
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	@Test
	public void testInsert() {
		Member mem1 = new Member();
		mem1.setId("member");
		mem1.setPassword(encoder.encode("abcd"));
		mem1.setName("둘리");
		mem1.setRole(Role.ROLE_MEMBER);
		mem1.setEnabled(true);
		memRepo.save(mem1);
		
		Member mem2 = new Member();
		mem2.setId("admin");
		mem2.setPassword(encoder.encode("abcd"));
		mem2.setName("도우너");
		mem2.setRole(Role.ROLE_ADMIN);
		mem2.setEnabled(true);
		memRepo.save(mem2);
		
		for (int i = 1; i <= 13; i++) {
			Board board = new Board();
			board.setMember(mem1);
			board.setTitle(mem1.getName() + "가 등록한 게시물 " + i);
			board.setContent(mem1.getName() + "가 등록한 게시물 " + i);
			boardRepo.save(board);
		}
		
		for (int i = 1; i <= 3; i++) {
			Board board = new Board();
			board.setMember(mem2);
			board.setTitle(mem2.getName() + "가 등록한 게시물 " + i);
			board.setContent(mem2.getName() + "가 등록한 게시물 " + i);
			boardRepo.save(board);
		}
	}
	
	// @Test
	public void testGetBoard() {
		Board board = boardRepo.findById(1L).get();
		
		System.out.println("[ " + board.getSeq() + "번 게시물 상세 정보 ]");
		System.out.println("제목: " + board.getTitle());
		System.out.println("작성자: " + board.getMember().getName());
		System.out.println("내용: " + board.getContent());
		System.out.println("등록일: " + board.getCreateDate());
		System.out.println("조회수: " + board.getCnt());
	}
	
	// @Test
	public void testGetBoardList() {
		Member member = memRepo.findById("admin").get();
		List<Board> boardList = member.getBoardList();
		
		System.out.println("[ " + member.getName() + "가 등록한 게시글 ]");
		for (Board b : boardList) {
			System.out.println("--->" + b);
		}
	}
}
