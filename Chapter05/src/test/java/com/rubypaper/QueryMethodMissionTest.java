package com.rubypaper;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@SpringBootTest
public class QueryMethodMissionTest {
	
	@Autowired
	private BoardRepository boardRepo;
	
	@BeforeAll		// 모든 테스트 실행 전에 한 번 실행
	static void dataPrepare(@Autowired BoardRepository boardRepo) {
		System.out.println("dataPrepare()");
		Random rd = new Random();
		for (int i = 1; i <= 100; i++) {
			Board board = new Board();
			board.setTitle("테스트 제목 " + i);
//			board.setWriter("테스터");
			board.setContent("테스트 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(rd.nextLong(100L));
			boardRepo.save(board);
		}
	}
	
	//@Test
	public void findByTitleContaining( ) {
		List<Board> list = boardRepo.findByTitleContaining("1");
		System.out.println("검색 결과");
		for (Board board : list) {
			System.out.println("---> " + board.toString());
		}
	}
	
	//@Test
	public void findByTitleContainingAndCntGreaterThan( ) {
		List<Board> list = boardRepo.findByTitleContainingAndCntGreaterThan("1", 50);
		System.out.println("검색 결과");
		for (Board board : list) {
			System.out.println("---> " + board.toString());
		}
	}
	
	//@Test
	public void findByCntBetweenOrderBySeq( ) {
		List<Board> list = boardRepo.findByCntBetweenOrderBySeq(10, 50);
		System.out.println("검색 결과");
		for (Board board : list) {
			System.out.println("---> " + board.toString());
		}
	}
	
	@Test
	public void findByTitleContainingOrContentContainingOrderBySeqDesc( ) {
		List<Board> list = boardRepo.findByTitleContainingOrContentContainingOrderBySeqDesc("10", "2");
		System.out.println("검색 결과");
		for (Board board : list) {
			System.out.println("---> " + board.toString());
		}
	}
}
