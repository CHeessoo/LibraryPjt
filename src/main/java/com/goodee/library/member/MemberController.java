package com.goodee.library.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member")
public class MemberController {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	MemberService memberService;
	
	// 회원가입 화면 이동
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String openMemberCreate() {
		LOGGER.info("[MemberController] openMemberCreate();");
		// WEB-INF/views/member/create.jsp
		return "member/create";
	}
	
	// 회원가입 기능 수행
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createMember(MemberVo vo) {
		LOGGER.info("[MemberController] createMember();");
		
		// insert 성공시 이동할 페이지
		String nextPage = "member/create_success";
		
		// insert 실패시 이동할 페이지
		if(memberService.createMember(vo) <= 0) { 
			nextPage = "member/create_fail";     
		}
		return nextPage;
	}
	
	
}
