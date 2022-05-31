package com.jenniesoft.board.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jenniesoft.board.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

	//게시글 번호를 이용해서 삭제하는 메서드
	@Modifying// 락을 걸어주세요~~ delete와 update는 반드시 붙여야 함
				//select를 두번이상 해도 붙여야 함
	@Query("delete from Reply r where r.board.bno = :bno")
	public void deleteByBno(@Param("bno") Long bno);
	
	//public void deleteByRno(Long rno);//이렇게 @Query안했으니까 자기가 알아서 붙일꺼니까 이때는 @Modifying도 알아서 붙일거니까 안해도 됨!
}
