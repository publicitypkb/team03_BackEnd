package com.jenniesoft.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jenniesoft.board.dto.BoardDTO;
import com.jenniesoft.board.dto.PageRequestDTO;
import com.jenniesoft.board.dto.PageResultDTO;
import com.jenniesoft.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller 
@Log4j2
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;

	@GetMapping({"/", "/board/list"})
	public String list(PageRequestDTO pageRequestDTO, Model model) { //리다이렉트 할거 아니니까 그냥 모델을 씀
		log.info("목록보기 요청.."+pageRequestDTO);
		
		model.addAttribute("result", boardService.getList(pageRequestDTO)); //pageResultDTO가 아님 이건 실제 컨트롤러에서 부르지 않음
		return "board/list";
	}

	//게시물 작성 처리 메서드
	@GetMapping("/board/register")
	public void register() {
		log.info("게시물 등록 폼 요청..");
	}
	@PostMapping("/board/register")
	public String register(BoardDTO dto, RedirectAttributes ratt) { //@RequestParam("dto")이딴거 막쓰면 에러난다 이건 파라미터가 넘어와야한다.
		log.info("게시물 등록 처리중.."+dto);
		//게시물 등록 (한줄로 되네 헐~~~) 기존에 register메서드를 서비스 작업에서 로직처리 해놨으니까!!!! 만들어놓고 bno반환하게 메서드 만들어놨으니까
		Long bno = boardService.register(dto);
		
//		BoardDTO result = BoardDTO.builder().content(dto.getContent()).title(dto.getTitle()).build();
//		boardService.register(result);
		
		ratt.addFlashAttribute("msg" , bno+" 삽입쓰");
		return "redirect:/board/list";
		
	}
	//상세보기
	@GetMapping( {"/board/read", "/board/modify"})
	//@ModelAttribute("이름")파라미터 받아서 이름으로 다음 요청에 넘겨겨
	public void read(@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Long bno, Model model) {//bno는 맞고 / 겟매핑이니까 Model/ 하나가 더필요해 pageRequestDTO
		log.info(bno);
		boardService.getBoard(bno); //잘해따
		log.info(boardService.getBoard(bno));
		model.addAttribute("dto", boardService.getBoard(bno));
		
	}
	//수정 처리 메서드
	@PostMapping("/board/modify")
	//수정은 이전 목록보기로 돌아갈 수 있어어 목록보기 데이터를 같이 보내줘야함. 등록은 무조건 이전페이지로 돌아가니까 상관없음. 전통 상세보기는 내용만 보여줌. 최근상세보기는 아래 목록 같이 보여줌(모바일) << (11:34)
	public String modify(BoardDTO dto, @ModelAttribute("requestDTO")PageRequestDTO pageRequestDTO , RedirectAttributes ratt) {//post니까 리다이렉트할거니까 model안쓴다 medelAttributes, session<<<<(11:36)
		log.info("수정처리중.."+dto);
		log.info("수정처리중.."+pageRequestDTO);
		boardService.modifyBoard(dto);
		
		ratt.addAttribute("page", pageRequestDTO.getPage());
		ratt.addAttribute("type", pageRequestDTO.getType());
		ratt.addAttribute("keyword", pageRequestDTO.getKeyword());
		ratt.addAttribute("bno", dto.getBno());
		return "redirect:/board/read";//단순하게 list로 갈거라면 bno는 필요하지 않다 <<<11:46
	}
	//삭제 
	@PostMapping("/board/remove")
	public String remove(long bno, RedirectAttributes ratt) {
		log.info("삭제처리.."+bno);
		boardService.removeWithReplies(bno);
		ratt.addFlashAttribute("msg",bno+"삭제");
		
		return "redirect:/board/list";
	}
}
