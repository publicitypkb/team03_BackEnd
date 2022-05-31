package com.jenniesoft.board.service;

import java.util.Optional;
import java.util.function.Function;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jenniesoft.board.dto.BoardDTO;
import com.jenniesoft.board.dto.PageRequestDTO;
import com.jenniesoft.board.dto.PageResultDTO;
import com.jenniesoft.board.model.Board;
import com.jenniesoft.board.model.Member;
import com.jenniesoft.board.persistence.BoardRepository;
import com.jenniesoft.board.persistence.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor //autowired대신 웹서비스는 미리 불러놓는게 빠른 서비스 제공(오전에설명한 부분)
@Log4j2//로그를 집어넣으면 syso 하지말고 이거로 해라!
public class BoardServiceimpl implements BoardService{
	private final BoardRepository boardRepository;
	
	@Override
	public Long register(BoardDTO dto) {
		log.info(dto); 
		
		Board board = dtoToEntity(dto);
		boardRepository.save(board);
		return board.getBno();
	}

	@Override
	public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
		log.info(pageRequestDTO);
		
		//Entity를 DTO로 변환해주는 함수 생성
		//Repository의 메서드의 결과가 Object[] 인데 이 배열의 요소를 가지고 BoardDTO를 생성해서 출력해야함
		Function<Object[], BoardDTO> fn = (en -> 
			entityToDTO((Board)en[0], (Member)en[1], (Long)en[2]));
		//데이터를 조회 - bno의 내림차순 적용
		//상황에 따라서는 regDate나 modDate로 정렬하는 경우도 있음
		Page<Object[]> result = boardRepository.getBoardWithReplyCount(
			pageRequestDTO.getPageable(Sort.by("bno").descending()));
		
		return new PageResultDTO<>(result, fn);
	}
	@Transactional //테스트가 아니라 여기 붙여야함!!!!!!!
	@Override
	public BoardDTO getBoard(Long bno) {
		//bno를 이용해서 하나의 데이터를 가져온다 
		//Board, Member, Long순서대로 반환하는 Object[]배열이었으니까
//		BoardDTO result = (BoardDTO)boardRepository.getBoardByBno(bno);
		Object result = boardRepository.getBoardByBno(bno);
		Object [] ar = (Object [])result;
		
		return entityToDTO((Board)ar[0], (Member)ar[1], (Long)ar[2]);
	}

	private final ReplyRepository replyRepository; //@Autowired는 맨 위에 reqqiredArgsConstructor있음
	@Transactional//★★★★★★★★★★★★★★★★★★★★★★여기에 부치는것이여 테스트가 아니라 
				//서비스에서는 delete가 update작업이 두번이상의 작업이 발생하면 붙여야 함. - 에러는 안뜨지만 튕겼을 때 문제가 됨
				// 이 안에서 boardRepository의 modifying이 되어 있는걸 부를 땐 꼭 붙여야 함
	@Override
	public void removeWithReplies(Long bno) {
		// 댓글이 물려있으니까 댓글을 먼저 삭제합니다~~~~. 그런데 그건 replyRepository에 있으니까 final로 위에 가져옴
		replyRepository.deleteByBno(bno);
		// 게시글 삭제
		boardRepository.deleteById(bno);
		
	}

	@Transactional //수정이니까 붙여주어여
	@Override
	//ㅡㅡㅡㅡㅡㅡㅡ ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void modifyBoard(BoardDTO boardDTO) { 
		//데이터 존재여부확인먼저!
		Optional<Board> board = boardRepository.findById(boardDTO.getBno()); //담아오는건 DTO로 담아왔지만 필요한건 번호고 리턴값은 하나만 가져오는것이여
		if(board.isPresent()) {
			board.get().changeTitle(boardDTO.getTitle()); //.get()으로 입력받은 boardDTO
			board.get().changeContent(boardDTO.getContent());
			
			boardRepository.save(board.get());
		}
	}

}
