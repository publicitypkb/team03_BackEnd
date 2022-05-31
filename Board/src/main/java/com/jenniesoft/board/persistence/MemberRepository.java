package com.jenniesoft.board.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jenniesoft.board.model.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

}
