package com.rubypaper.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardRepository boardRepo;
	
	@Override
	public List<Board> getBoardList() {
		return boardRepo.findAll();
	}
	
	@Override
	public Board getBoard(Board board) {
		return boardRepo.findById(board.getSeq()).get();
	}
	
	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);	
	}
	
	@Override
	public void updateBoard(Board board) {
		Board oldBoard = boardRepo.findById(board.getSeq()).get();
		oldBoard.setTitle(board.getTitle());
		oldBoard.setContent(board.getContent());
		boardRepo.save(oldBoard);
	}
	
	@Override
	public void deleteBoard(Board board) {
		boardRepo.deleteById(board.getSeq());	
	}
}
