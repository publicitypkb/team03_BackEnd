package com.jenniesoft.board.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
//@Table 테이블이름 따로 지정할필요없응까

@Getter
@ToString(exclude="member")
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class Reply extends BaseEntity{
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO) //identity로 하면 1부터 순차적으로 들어가는데 / auto로 하면 DB에서 순서 없이 들어감 // auto로하면 숫자로밖에 안들어감 identity로 하면 문자로도 지정가능허다~~
	private Long rno;
	private String text;
	private String replyer;
	
	@ManyToOne
	private Board board;
	
}

