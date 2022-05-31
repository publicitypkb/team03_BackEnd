package com.jenniesoft.board;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jenniesoft.board.dto.BoardDTO;
import com.jenniesoft.board.dto.PageRequestDTO;
import com.jenniesoft.board.dto.PageResultDTO;
import com.jenniesoft.board.service.BoardService;

@SpringBootTest
public class ServiceTest {
	@Autowired
	private BoardService boardService;
	
	//@Test
	public void testRegister() {
		BoardDTO dto = BoardDTO.builder().title("Test2").content("테스트2").memberEmail("jennie100@aaa.com").build();
		Long bno = boardService.register(dto);
		System.out.println(bno);
	}
	//@Test
	public void testList() {
		PageRequestDTO pageRequestDTO = new PageRequestDTO();
		PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);
		
		for(BoardDTO boardDTO : result.getDtoList()) {
			System.out.println(boardDTO);
		}
	}
	//@Test //<<<<<<<<<<<<<<<<<<<<
	public void testGetBoard() {
		Long bno = 40L; //있는 번호 bno하나 만들고
		BoardDTO boardDTO = boardService.getBoard(bno);
		System.out.println(boardDTO);
	}
	//@Test //@Transactional//이메서드 안의 작업은 하나의 트랜젝션으로 처리해달라고 요청. 여기에 이걸 붙이는게 아님
	public void testDeleteBoard() {
		Long bno = 81L;
		boardService.removeWithReplies(bno);
	}
	@Test
	public void testModifyBoard() {
		BoardDTO boardDTO = BoardDTO.builder().bno(1L)
				.title("제목수정").content("내용수정").build(); //빌더를 서비스 구현에서 하는게 아니라 여기서 하는것이군!!!
		boardService.modifyBoard(boardDTO);
	}
	
}
