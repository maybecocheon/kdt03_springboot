package com.rubypaper;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.rubypaper.domain.Board;

public class JPAClient {
	
	// EntityManagerFactory 생성 => 서버당 하나
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04");

	public static void insert() {
		// EntityManager 생성 => 트랜잭션당 하나
		EntityManager em = emf.createEntityManager();

		// Transaction 생성
		EntityTransaction tx = em.getTransaction();

		try {
			// Transaction 시작 => 얘 있어야 실제 DB에 입력 가능함
			tx.begin();

			// DB에 저장할 객체 생성
			Board board = new Board();
			board.setTitle("JPA 제목");
			board.setWriter("관리자");
			board.setContent("JPA 글 등록 잘되네요");
			board.setCreateDate(new Date());
			board.setCnt(0L);

			// 글 등록
			em.persist(board);

			// Transaction commit
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();

			// Transaction rollback
			tx.rollback();
		} finally {
			// 사용한 리소스 객체 닫기
			em.close();
		}
	}

	public static void select() {
		EntityManager em = emf.createEntityManager();
		
		try {
			// 글 상세 조회
			Board searchBoard = em.find(Board.class, 1L);
			System.out.println("---> " + searchBoard.toString());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}
	
	public static void selectQuery() {
		EntityManager em = emf.createEntityManager();
		
		try {
			// Board 클래스에서 select 쿼리 실행해 Board.class를 반환받는 jpql 
			List<Board> lst = em.createQuery("SELECT b FROM Board b", Board.class).getResultList();
			lst.stream().forEach(System.out::println);
			
			@SuppressWarnings("unchecked")
			// board 테이블에서 select 쿼리 실행해 Board.class를 반환받는 sql
			List<Board> lst2 = em.createNativeQuery("SELECT * FROM board", Board.class).getResultList();
			for (Board board : lst2) {
				System.out.println(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public static void main(String[] args) {
		
		insert();
		
		if (emf != null) {
			emf.close();
		}
	}
}
