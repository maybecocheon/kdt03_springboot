package com.rubypaper.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.dto.BoardDTO;

//JpaRepository 내 메서드 자동으로 구현됨
public interface BoardRepository extends JpaRepository<Board, Long> {
	// 내가 만든 쿼리 메서드
	List<Board> findByTitle(String searchKeyword);
	List<Board> findByContentContaining(String searchKeyword);
	List<Board> findByTitleContainingOrContentContaining(String title, String content);
	List<Board> findByTitleContainingOrderBySeqDesc(String searchKeyword);
	
	// 미션
	List<Board> findByTitleContaining(String searchKeyword);
	List<Board> findByTitleContainingAndCntGreaterThan(String title, int cnt);
	List<Board> findByCntBetweenOrderBySeq(int start, int end);
	List<Board> findByTitleContainingOrContentContainingOrderBySeqDesc(String title, String content);
	
	// 페이징
	// List<Board> findByTitleContaining(String searchKeyword, Pageable paging);
	List<Board> findByTitleContainingOrderBySeqDesc(String searchKeyword, Pageable paging);
	Page<Board> findByTitleContaining(String searchKeyword, Pageable paging);

	// Query 어노테이션
	// jpql
	@Query("SELECT b FROM Board b WHERE b.title LIKE %?1% ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest1(String searchKeyword);
	
	@Query("SELECT b FROM Board b WHERE b.title LIKE %:searchKeyword% ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest2(@Param("searchKeyword") String searchKeyword);
	
	@Query("SELECT b.seq, b.title, b.writer, b.createDate FROM Board b WHERE b.title LIKE %:searchKeyword% ORDER BY b.seq DESC")
	List<Object[]> queryAnnotationTest3(String searchKeyword); // 파라미터명 같으면 Param 어노테이션 생략 가능
	
	@Query("SELECT new com.rubypaper.domain.dto.BoardDTO(b.seq, b.title, b.writer) FROM Board b "
				+ "WHERE b.title LIKE %:searchKeyword% ORDER BY b.seq DESC")
	List<BoardDTO> queryAnnotationTest4(String searchKeyword);
	
	@Query("SELECT b FROM Board b ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest6(Pageable paging);
	
	// 네이티브 쿼리 => 객체로 못 받고 배열로만 받아야 함
	@Query(value="SELECT * FROM board WHERE title LIKE '%'||:searchKeyword||'%' ORDER BY seq DESC",
			nativeQuery=true)
	List<Object[]> queryAnnotationTest5(String searchKeyword);
}
