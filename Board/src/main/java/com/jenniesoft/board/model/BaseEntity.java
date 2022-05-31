package com.jenniesoft.board.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass //테이블로 생성할 필요가 없음
@EntityListeners(value= {AuditingEntityListener.class}) //데이터베이스 작업을 감시
@Getter
abstract public class BaseEntity {
	//생성한 시간을 저장하는데 컬럼 이름은 regdate이고 수정될 수 없다
	@CreatedDate// 만들어진시간을 넣어줘
	@Column(name="regdate", updatable=false)
	private LocalDateTime regDate;
	
	@LastModifiedDate// 고친시간을 넣어줘
	@Column(name="moddate", updatable=false)
	private LocalDateTime modDate;
	
}
