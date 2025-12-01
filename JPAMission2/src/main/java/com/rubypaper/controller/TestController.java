package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.DynamicBoardRepository;

import lombok.Data;

@Data
class BoardFilter {
	private String key;
	private String value;
	private Integer pageNum = 0;
	private Integer pageSize = 5;
}

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private DynamicBoardRepository boardRepo;
	
	@GetMapping("/boardlist")
	public List<Board> getBoardList(BoardFilter bf) {
		List<Board> list = boardRepo.findAll();
		if (bf.getPageNum() == 0) {
			
		}
		return list;
	}
	
	//@GetMapping("/boardlist?pageNum={pageNum}")
}
