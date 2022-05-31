package com.jenniesoft.board.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data 
public class PageResultDTO<DTO, EN> {
	private List<DTO> dtoList; //
	private int totalPage, page, size; //전체페이지, 현재 , 페이지당 끊을단위
	private int start, end; //시작과 끝페이지 번호
	private boolean prev, next; //이전페이지와 다음페이지 존재여부
	
	private List<Integer> pageList; //출력할 페이지번호 목록 <<<<<<<<<<<<<<<
	
	//페이지리스트가 뭐냐면 화면에 표시할 시작 끝번호가 뭔지를 결정하는 메서드
	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() +1 ;//1을더해야함 안그러면 페이지 음수나옴
		this.size = pageable.getPageSize();
		
		int tempEnd = (int)(Math.ceil(page/10.0))*10;
		start = tempEnd - 9;
		prev = start > 1;
		end = totalPage > tempEnd ? tempEnd: totalPage;
		next = totalPage > tempEnd;
		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
	//페이지랑 펑션을 매개변수로 갖고 페이지리스트 함수를 호출하는 메서드
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		totalPage = result.getTotalPages();
		makePageList(result.getPageable());
	}
}
