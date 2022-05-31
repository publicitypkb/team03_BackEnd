package com.jenniesoft.board.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jenniesoft.board.model.Board;

//save, findOne, findAll, exist, count, delete, findAll(pageable), findAll(sort), flush, saveAndFlush 등 
//공통작업을 JpaRepository 인터페이스로 빼놓은 거니까 이걸 인터페이스에서 상속받음. <타입엔터티,식별자>
//없는 메서드는 List<Member> findByName(String userName); 이렇게 하면 알아서 jpql도 짜줌  ㅡ 메서드 이름으로 쿼리
//구현 List<Member> member = memberRepository.findByName("hello"); 하면  ㅡ 사용코드
//sql문 실행됨 select * from member m where m.name='hello'
//List<Member> findByName(String userName, Sort sort); 이렇게 하면 ㅡ 이름으로 검색 + 정렬 ㅡ 파라미터로 sort넣기만하 됨 
//sql문 실행됨 select * from member m where m.name='hello' order by age desc ㅡ 
//Page<Member> findByName(String userName, Pageable pageable); 이렇게 하면 ㅡ 이름으로 검색 + 페이징
//Page<Member> findByName(String userName, Pageable pageable); 이렇게 하면 ㅡ 이름으로 검색 + 정렬 + 페이징
//Pageable page = new PageRequest(1, 20, new Sort...);
//Page<Member> result = memberRepository.findByName("hello", page);
//in total = result.getTotalElements();
//List<Member> members= result.getContent(); 내부에 다 구현되어있음 이런 것들이
//
//sql문 실행됨 
//select * 
//from
//	(select row_.*.rownum rownum_
//		from
//			(select m.*
//			from member m where m.name='hello'
//			order by m.name
//			)row_
//		where rownum <=?
//	)
//where rownum_>?
//
//인터페이스 이름으로 하는거 말고 직접하고 싶으면 @Query("jpql쿼리문 직접 작성") 조인도들어감 jpaRepository 상속받은 인터페이스 내부 메서드 위에 작성. 
//반환타입도 직접 지정. 하나는 Member, 여러개컬랙션은 List<Member> 없으면 각각 null, [] empty collection나오지. Optional도됨 
//QueryDSL은 SQL과 JPQL을 코드로 작성할 수 있도록 도와주는 빌더API : sql과 jpql은 문자라서 타입체크가 불가능. 해당로직 실행전까지 작동확인 불가능 로딩때나 가능하고 컴파일에 불가능. 
//dsl은 문자가 아닌 코드라, 컴파일시점 오류 발견, 코드 자동완성, 단순함 jqpl과 거의비슷, 동적쿼리다. Qmember.java생성. querydsl전용파일.
public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository{

	//Board테이블에서 데이터 가져올 때 Member정보도 같이 가져오는 메서드 
	//(리턴타입은 Enity말고 Object로하자 글번호 하나에 멤버 하나니까
	@Query("select b, w from Board b left join b.member w where b.bno= :bno")
	Object getBoardWidthMember(@Param("bno")Long bno);

	//특정 번호의 보드와 그의 댓글 정보 같이 가져오는 메서드 
	//(리턴타입 글하나에 여러개 댓글 있늬까 List<Object[]>
	//sql의 문제 select를 먼저 쓸 수 없어
//	@Query("select b, r from Board b left join b.reply r where b.bno= :board_bno")
	@Query("select b, r from Board b left join Reply r on r.board = b where b.bno= :bno")
	List<Object []> getBoardWithReply(@Param("bno") Long bno);
	
	//목록 보기 메서드
	//페이지를 리턴할 때는 value와 countQuery가 둘 다 만들어져야 함/ JPQL에서는 카운트 처리할 때 countQuery같이 넣음 (배웠던거)
	@Query(value="select b, w, count(r) "
			+"from Board b left join b.member w left join Reply r On r.board = b "
			+"group by b",
			countQuery="select count(b) from Board b")//페이징이니까 countQuery까지
	Page<Object []> getBoardWithReplyCount(Pageable pageable);
	//게시글 번호로 동일한 데이터 가져오는 메서드 
	@Query(value="select b, w, count(r) "
			+"from Board b left join b.member w left join Reply r On r.board = b "
			+"where b.bno = :bno")//페이징 아니고 하나만 가져올거니까 countQuery필요없어
	Object getBoardByBno(@Param("bno") Long bno);
	
}
