package com.jenniesoft.board.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder
public class PageRequestDTO {
	private int page;//현재번호
	private int size;//페이지당 출력할 데이터개수
	private String type;//검색 항목
	private String keyword;//검색 키워드
	
	//생성자
	public PageRequestDTO() {
		this.page=1;
		this.size=10;
	}
	
	//페이지 검색 가능 객체 생성 메서드
	//Pageable pageable = PageRequest.of(0,10,sort); // sort조건을 넣으면 sql문 뒤에 order by 조건이 생성됨
	//Page<Memo> result = memoRepository.findAll(pageable); 
	public Pageable getPageable(Sort sort) {
		return PageRequest.of(page-1, size, sort);
	}
}
