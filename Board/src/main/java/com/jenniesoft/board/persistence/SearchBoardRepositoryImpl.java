package com.jenniesoft.board.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.jenniesoft.board.model.Board;
import com.jenniesoft.board.model.QBoard;
import com.jenniesoft.board.model.QMember;
import com.jenniesoft.board.model.QReply;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2      
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

	public SearchBoardRepositoryImpl() {
		super(Board.class);
	}

	@Override
	public Board search2() {
		log.info("search...");
		/*
		QBoard board = QBoard.board;
		JPQLQuery<Board> jpqlQuery = from(board);
		//bno가 50인 데이터 조회 위한 메서드 호출 (쿼리를 만듦)
		jpqlQuery.select(board).where(board.bno.eq(50L));
		//실행
		List<Board> result = jpqlQuery.fetch();
		log.info("결과:" + result);
		*/
		
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
		QMember member = QMember.member;
		//
		//select *
		//from board b , Member m
		//where b.email = m.email
		JPQLQuery<Board> jpqlQuery = from(board);
		jpqlQuery.leftJoin(member).on(board.member.eq(member));
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
		
		
		return null;
	}

	@Override
	public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
		log.info("search...");
		
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;
		QMember member = QMember.member;
		//
		//select *
		//from board b , Member m
		//where b.email = m.email
		JPQLQuery<Board> jpqlQuery = from(board);
		jpqlQuery.leftJoin(member).on(board.member.eq(member));
		jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
		
		
		return null;

	}

}
