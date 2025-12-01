package com.rubypaper;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@SpringBootTest
public class QueryMethodTest {
	@Autowired
	private BoardRepository boardRepo;
	/*	
	@BeforeAll		// 모든 테스트 실행 전에 한 번 실행
	static void dataPrepare(@Autowired BoardRepository boardRepo) {
		System.out.println("dataPrepare()");
		for (int i = 1; i <= 200; i++) {
			Board board = new Board();
			board.setTitle("테스트 제목 " + i);
			board.setWriter("테스터");
			board.setContent("테스트 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(0L);
			boardRepo.save(board);
		}
	}
	
	//@Test
	public void testFindByTitle() {
		List<Board> boardList = boardRepo.findByTitle("테스트 제목 10");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
	
	//@Test
	public void testByContentContaining() {
		List<Board> boardList = boardRepo.findByContentContaining("17");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
	
	//@Test
	public void testByTitleContainingOrContentContaining() {
		List<Board> boardList = boardRepo.findByTitleContainingOrContentContaining("17", "15");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
	
	//@Test
	public void testByTitleContainingOrderBySeqDesc() {
		List<Board> boardList = boardRepo.findByTitleContainingOrderBySeqDesc("17");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
	
	@Test
	public void testFindByTitleContaining() {
		// 총 게시물을 5개씩 나누고 그중 0번째 페이지인 애를 불러옴
		Pageable paging = PageRequest.of(1, 5, Sort.Direction.DESC, "seq");
		// List<Board> boardList = boardRepo.findByTitleContaining("제목", paging);
		Page<Board> pageInfo = boardRepo.findByTitleContaining("제목", paging);
		System.out.println("PAGE INFO: " + pageInfo.getNumber());
		System.out.println("PAGE SIZE: " + pageInfo.getSize());
		System.out.println("TOTAL PAGES: " + pageInfo.getTotalPages());
		System.out.println("TOTAL COUNT: " + pageInfo.getTotalElements());
		System.out.println("RESULT: " + pageInfo.getNumberOfElements());
		System.out.println("PREVIOUS PAGE? " + pageInfo.hasPrevious());
		System.out.println("NEXT PAGE? " + pageInfo.hasNext());
		System.out.println("LAST PAGE? " + pageInfo.isLast());
		System.out.println("PREVIOUS: " + pageInfo.previousPageable());
		System.out.println("NEXT: " + pageInfo.nextPageable());
		List<Board> boardList = pageInfo.getContent();
		
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
	
	//@Test
	public void findByTitleContainingOrderBySeqDesc() {
		// 페이징에서 설정한 정렬보다 메서드에서 설정한 정렬이 우선됨
		Pageable paging = PageRequest.of(0, 5, Sort.Direction.ASC, "seq");
		List<Board> boardList = boardRepo.findByTitleContainingOrderBySeqDesc("제목", paging);
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("---> " + board.toString());
		}
	}
 	*/
}
