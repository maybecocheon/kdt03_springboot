package com.rubypaper.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@RestController
@RequestMapping("/api")
public class TestController {

	@Autowired
	private BoardRepository boardRepo;

	@GetMapping("/board")
	public List<Board> getBoards() {
		// 검색 결과 목록 리턴
		List<Board> list = boardRepo.findAll();
		return list;
	}

	@GetMapping("/board/{seq}")
	public Board getBoard(@PathVariable Long seq) {
		// 검색 결과 객체 리턴
		Board board = boardRepo.findById(seq).get();
		return board;
	}

	@PostMapping("/board")
	public Board postBoard(@RequestBody Board board) {
		// 입력 객체 리턴
		board.setCreateDate(new Date());
		board.setCnt(0L);
		boardRepo.save(board);
		return board;
	}

	@PutMapping("/board/{seq}")
	public Board putBoard(@PathVariable Long seq, @RequestBody Board board) {
		// 수정 객체 리턴
		Board oldBoard = boardRepo.findById(seq).get();
		board.setSeq(seq);
		board.setCreateDate(oldBoard.getCreateDate());
		board.setCnt(oldBoard.getCnt());
		boardRepo.save(board);
		
		return board;
	}

	@PatchMapping("/board/{seq}")
	public Board patchBoard(@PathVariable Long seq, @RequestBody Board board) {
		// 수정 객체 리턴
		Board oldBoard = boardRepo.findById(seq).get();

		if (board.getTitle() != null)
			oldBoard.setTitle(board.getTitle());
		if (board.getContent() != null)
			oldBoard.setContent(board.getContent());
//		if (board.getWriter() != null)
//			oldBoard.setWriter(board.getWriter());
		
		boardRepo.save(oldBoard);

		return oldBoard;
	}
	
	@DeleteMapping("/board/{seq}")
	public Board deleteBoard(@PathVariable Long seq) {
		// 삭제 객체 리턴
		Board board = boardRepo.findById(seq).get();
		boardRepo.deleteById(seq);
		return board;
	}
}
