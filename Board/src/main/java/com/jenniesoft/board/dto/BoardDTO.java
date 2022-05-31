package com.jenniesoft.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	//기존에 Board Entity에 가지고 있는 것들
	private Long bno;
	private String title;
	private String content; 
	private LocalDateTime regDate;
	private LocalDateTime modDate;
	
	//+기타 필요한 정보 추가
	//회원에서 필요한 정보(메일, 이름) + 댓글 수
	private String memberEmail;
	private String memberName;
	
	private int replyCount;
}
