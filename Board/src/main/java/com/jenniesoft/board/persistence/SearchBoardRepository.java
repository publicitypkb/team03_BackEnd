package com.jenniesoft.board.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jenniesoft.board.model.Board;

public interface SearchBoardRepository {
	//QueryDsl적용한 SQL 실행하기 위한 메서드
	public Board search2();

	//검색 위한 메서드
	//3개의 항목을 묶어서 하나의 클래스로 표현해도 된다.
	Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
