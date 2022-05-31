package com.jenniesoft.board;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jenniesoft.board.model.Board;
import com.jenniesoft.board.model.Member;
import com.jenniesoft.board.model.Reply;
import com.jenniesoft.board.persistence.BoardRepository;
import com.jenniesoft.board.persistence.MemberRepository;
import com.jenniesoft.board.persistence.ReplyRepository;
import com.jenniesoft.board.persistence.SearchBoardRepository;

@SpringBootTest
public class RepositoryTest {

//	c:생성자 게터세터 또는
//	p:필요할 때 만드는거
//	autowired < contructor로만든거 쓰라고 한 이유 스프링은 서버를 만드는 것 속도가 더 중요하니까~~~~
// 	안드로이드는 클라이언트쪽이니까 레이지를 사용
	@Autowired
	private MemberRepository memberRepository;
	
	//@Test
	public void insertMembers() {
		IntStream.rangeClosed(1,100).forEach(i->{
			Member member = Member.builder().email("jennie"+i+"@aaa.com")
					.password("1111").name("아무개"+i).build();
			memberRepository.save(member);
		});
	}
	
	@Autowired
	private BoardRepository boardRepository;
	
	//@Test
	public void insertBoards() {
		IntStream.rangeClosed(1,100).forEach(i->{
			Member member = Member.builder().email("jennie1@aaa.com").build();
			Board board  = Board.builder().title("제목..."+i)
					.title("제목..."+i)
					.content("내용..."+i)
					.member(member)
					.build();
			boardRepository.save(board);
			
		});
	}
	
	@Autowired
	private ReplyRepository replyRepository;
	
	//@Test
	public void insertReplys() {
		IntStream.rangeClosed(1, 300).forEach(i->{
			long bno = (long)(Math.random()*100)+1; //1부터 100까지의 랜덤숫자 (board에 있는 수 범위)
			Board board = Board.builder().bno(bno).build();
			
			Reply reply = Reply.builder().text("댓글..."+i)
					.board(board)
					.build();
			replyRepository.save(reply);
			
		});
	}
	
	//하나의 보드 데이터 가져오기
	@Transactional
	//@Test
	public void readBoard() {
		Optional<Board> result =boardRepository.findById(100L);
		//데이터 출력
//		System.out.println(result.get()); //bno로 가져온 board 데이터 한개
//		System.out.println(result.get().getMember());// 거기에 연결된 member 정보
		System.out.println(result.toString());
	}
	//하나의 리플 데이터 가져오기
//	@Transactional
//	@Test
	public void readReply() {
		Optional<Reply> result = replyRepository.findById(446L);
		System.out.println(result.get());
//		System.out.println(result.get().getBoard());
//		System.out.println(result.get().getBoard().getMember());
//		System.out.println(result.get().getBoard().getMember().getName());
	}
	
	//@Test
	public void testReadWithWriter() {
		//데이터조회
		Object result = boardRepository.getBoardWidthMember(100L);
		System.out.println(result); //배열은 toString이 안만들어져있어서 주소값이 나와용 ★
		//JPQL경과가 Object인 경우에는 Object[]로 강제형변환해서 사용한다
		System.out.println(Arrays.toString((Object[])result));
	}
	
	//@Test
	public void testGetBoardWithReply() {
		List<Object []> result = boardRepository.getBoardWithReply(53L);
		for(Object[] ar:result) {
			System.out.println(Arrays.toString(ar));
		}
	}
	

	//@Test
	public void testBoardWithReplyCount() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
		Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);//리스트오브젝터배열아님 페이지로받음
		
		result.get().forEach(row->{
			Object[] ar = (Object[])row;
			System.out.println(Arrays.toString(ar));
		});
	}
	@Test
	public void testWithByBno() {
		Object result = boardRepository.getBoardByBno(100L);
		
		Object[] ar = (Object[])result;
		System.out.println(Arrays.toString(ar));
	}
	
	@Autowired
	SearchBoardRepository searchBoardRepository;
	//@Test 
	public void testSearch() {
		searchBoardRepository.search2();
	}

}
