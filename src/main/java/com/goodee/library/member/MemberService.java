package com.goodee.library.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	// 회원가입
	public int createMember(MemberVo vo) {
		LOGGER.info("[MemberService] createMember();");
		int result = 0; 
		// 유저(아이디) 중복 확인
		if(dao.isMemberCheck(vo.getM_id()) == false) {  // 아이디가 존재하지 않으면 
			result = dao.insertMember(vo);              // insert 동작
		}
		return result;
	}
	
	// 로그인
	public MemberVo loginMember(MemberVo vo) {          // 매개변수로 id, pw 전달 받음
		LOGGER.info("[MemberService] loginMember();");
		MemberVo loginedMember = dao.selectMember(vo);
		return loginedMember;
	}
	
	// 회원 목록
	public List<MemberVo> listupMember() {
		LOGGER.info("[MemberService] listupMember();");
		return dao.selectMemberList();
	}

}
