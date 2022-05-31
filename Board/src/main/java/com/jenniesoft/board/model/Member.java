package com.jenniesoft.board.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="tbl_member")

@Getter
@ToString
@Builder // 빌더가 있음 세터 없어도 되지만 세터가 없으면 부분적 수정 불가능하다 . 부분변경할수 있는 펑션 따로 만들면 상관없다
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity{
	
	@Id //프라이머리키지정
	private String email;
	private String password;
	private String name;
	
}

