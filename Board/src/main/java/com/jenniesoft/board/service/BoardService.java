package com.jenniesoft.board.service;

import com.jenniesoft.board.dto.BoardDTO;
import com.jenniesoft.board.dto.PageRequestDTO;
import com.jenniesoft.board.dto.PageResultDTO;
import com.jenniesoft.board.model.Board;
import com.jenniesoft.board.model.Member;

public interface BoardService {
	//게시물 등록을 위한 메서드
	Long register(BoardDTO dto); //보통 기본키를 리턴
	//목록보기 메서드 선언쓰
	PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
	//상세보기 메서드 : 리턴타입은 글상세정보  / 매개변수는 PK 
	BoardDTO getBoard(Long bno);
	
	//게시글 삭제 메서드
	void removeWithReplies(Long bno);
	
	//게시글 수정 메서드
	void modifyBoard(BoardDTO boardDTO);
	
	//DTO to Entity 메서드. 인터페이스에 메서드를 구현할 때는 default를 꼭 붙여야 한다.
	default Board dtoToEntity(BoardDTO dto) {
		Member member = Member.builder().email(dto.getMemberEmail()).build();
		
		Board board = Board.builder().bno(dto.getBno()).title(dto.getTitle()).content(dto.getContent()).member(member).build();
		
		return board;
	}
	default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {
		BoardDTO boardDTO = BoardDTO.builder().bno(board.getBno()).title(board.getTitle()).content(board.getContent()).regDate(board.getRegDate()).modDate(board.getModDate())
				.memberEmail(member.getEmail()).memberName(member.getName())
				.replyCount(replyCount.intValue())
				.build();
		return boardDTO;
	}
}
