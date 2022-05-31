package com.jenniesoft.board.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="board") 

@Getter
@ToString(exclude="member")
@Builder // 빌더가 있음 세터 없어도 되지만 세터가 없으면 부분적 수정 불가능하다 . 부분변경할수 있는 펑션 따로 만들면 상관없다
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity{
	
	@Id //프라이머리키지정 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //- 값은 시퀀스로/ auto로 하면 숫자밖에 안된다. mysql이나 오라클 시퀀스가 숫자밖에 안돼서 
	private Long bno;
	private String title;
	private String content;
	private String writer;
	
	//Member Entity를 N:1관계로 참조. 보드가 멤버를 물고있다. 연관관계를 설정해놓았다
	//reply는 board를 물고 있는데 , 거꾸로 board는 reply를 물고있지 않다
	@ManyToOne(fetch=FetchType.LAZY) 
	private Member member;
	
	//(6:03)수정할 수 있도록 
	public void changeTitle(String title) {
		this.title= title;
	}
	public void changeContent(String content) {
		this.content = content;
	}
	
}
