package com.rubypaper.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BoardException.class)
	public String handleCustomException(BoardException exception, Model model) {
		model.addAttribute("exception", exception);
		return "/errors/boardError";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleException(Exception exception, Model model) {
		model.addAttribute("exceptionMessage", exception.getMessage());
		model.addAttribute("stackTrace", exception.getStackTrace());
		return "/errors/globalError";
	}
	
	/*
	  - 예외 객체 접근에 대한 보안 이슈로 ${exception.messge}에서 에러가 발생
	  - 예외 객체를 전달하는 방식에서 예외 메시지를 전달하는 방식으로 수정
	  - 사용자 정의 예외 객체는 ${exception.messge}에서 에러가 발생하지 않음.
	 */
}
