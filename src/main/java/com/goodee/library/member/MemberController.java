package com.goodee.library.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	
	// 로그인 화면 이동
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String openLoginForm() {
		LOGGER.info("[MemberController] openLoginForm();");
		// views - member - login_form.jsp 이동
		return "member/login_form";
	}
	
	// 로그인 기능 
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginMember(MemberVo vo, HttpSession session) {  // 세션을 사용하고자 HttpSession을 파라미터에 의존성 주입 해줌
		LOGGER.info("[MemberController] loginMember();");
		
		// 로그인 성공시 이동할 페이지
		String nextPage = "member/login_success";
		// 서비스에서 로그인 정보를 전달받음
		MemberVo loginedMember = memberService.loginMember(vo);
		// 로그인 실패시(전달된 정보가 null인 경우) 이동할 페이지
		if(loginedMember == null) {
			nextPage = "member/login_fail";
		} else {  // 로그인 성공 시
			// 세션 정보 저장
			session.setAttribute("loginMember", loginedMember);  // session.setAttribute(key, value);  - key값은 String으로 설정
			// 세션 유지 정보 저장
			session.setMaxInactiveInterval(60*30);               // int값, 초단위로 지정 (1800이라고 적어도 되나 가독성을 위해 60*30으로 표기)
		}
		return nextPage;
	}
	
	// 로그아웃 기능
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutMember(HttpSession session) {
		LOGGER.info("[MemberController] logoutMember();");
		// 세션 무효화
		session.invalidate();
		// 로그아웃(세션정보 삭제) 후 홈으로 리턴
		return "redirect:/";
	}
	/*
	 * ModelAndView와 Model 사용법
	 * 둘 다 뷰 리졸버로
	 */
	// 회원 목록 이동 - ModelAndView(1)
//	@RequestMapping(method=RequestMethod.GET)
//	public ModelAndView listupMember() {
//		LOGGER.info("[MemberController] listupMember();");
//		
//		// 1. 목록 정보 조회
//		List<MemberVo> memberVos = memberService.listupMember();
//		// 2. 목록 전달
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("memberVos", memberVos);
//		// 3. 뷰 선택
//		mav.setViewName("member/listup");
//		return mav;
//	}
	// 회원 목록 이동 - ModelAndView(2)
//	@RequestMapping(method=RequestMethod.GET)
//	public ModelAndView listupMember(ModelAndView mav) {
//		LOGGER.info("[MemberController] listupMember();");
//		
//		// 1. 목록 정보 조회
//		List<MemberVo> memberVos = memberService.listupMember();
//		// 2. 목록 전달
//		mav.addObject("memberVos", memberVos);
//		// 3. 뷰 선택
//		mav.setViewName("member/listup");
//		return mav;
//	}
	
	// 회원 목록 이동 - Model(1)
	@RequestMapping(method=RequestMethod.GET)
	public String listupMember(Model model) {
		LOGGER.info("[MemberController] listupMember();");
		
		// 1. 목록 정보 조회
		List<MemberVo> memberVos = memberService.listupMember();
		// 2. 목록 전달
		model.addAttribute("memberVos", memberVos);
		return "member/listup";
	}
	
	
}
