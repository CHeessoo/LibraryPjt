package com.goodee.library.member;

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
	
	public int createMember(MemberVo vo) {
		LOGGER.info("[MemberService] createMember();");
		int result = 0; 
		// 유저(아이디) 중복 확인
		if(dao.isMemberCheck(vo.getM_id()) == false) {  // 아이디가 존재하지 않으면 
			result = dao.insertMember(vo);              // insert 동작
		}
		return result;
	}

}
