package com.goodee.library.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	// 
	
	// 회원 정보 수정 화면 이동
	@RequestMapping(value="/{m_no}", method=RequestMethod.GET)
	public String modifyMember(@PathVariable String m_no, HttpSession session) {  // 인터셉터 사용으로인해 컨트롤러 내 코드와 매개변수 session이 다 필요 없어짐
		LOGGER.info("[MemberController] modifyMember");
		
		// 다른 사람의 정보 수정 O
		// 1. url에 있는 m_no 기준 select
		// 2. 수정 화면
		
		// 내 정보만 수정 O
		// 1. 세션에 있는 m_no 기준
		// 2. 수정 화면
//		MemberVo loginedMemberVo = (MemberVo)session.getAttribute("loginMember");
//		String nextPage = "";
//		if(loginedMemberVo == null) { // null 체크
//			// 로그인 화면 이동
//			nextPage = "redirect:/member/login";
//		} else {
//			// 수정 화면 이동
//			nextPage = "member/modify_form";
//		}
		return "member/modify_form";
	}
	
	// 회원 정보 수정 기능
	@RequestMapping(value="/{m_no}", method=RequestMethod.POST)
	public String modifyMemberConfirm(MemberVo vo, HttpSession session) {
		LOGGER.info("[MemberController] modifyMemberConfirm();");
		// 1. 회원 정보 수정 (DB)
		int result = memberService.modifyMember(vo);
		if(result > 0) { 
			// 2. 세션 정보 변경
			MemberVo loginedMemberVo = new MemberVo();
			loginedMemberVo = memberService.getLoginedMemberVo(vo.getM_no());  // 수정된 정보 가져오기
			session.setAttribute("loginMember", loginedMemberVo);              // 세션에 수정된 정보 새로 저장
			session.setMaxInactiveInterval(60*30);                             // 세션값 30분 유지
			// 3. 성공 화면 이동
			return "member/modify_success";
			
		} else {   
			// 3. 실패 화면 이동
			return "member/modify_fail";
		}
	}
	
	
	// 비밀번호 설정 화면 이동
	@RequestMapping(value="/findPassword", method=RequestMethod.GET)
	public String findPasswordForm() {
		LOGGER.info("[MemberController] findPasswordForm();");
		return "member/find_password_form";
	}
	
	// 비밀번호 설정 기능
	@RequestMapping(value="/findPassword", method=RequestMethod.POST)
	public String findPasswordConfirm(MemberVo vo) {
		LOGGER.info("[MemberController] findPasswordConfirm();");
		
		int result = memberService.findPasswordConfirm(vo);
		if(result <= 0) {
			// 정보가 일치하지 않은 경우
			return "member/find_password_fail";
		} else {
			// 정보가 일치한 경우
			return "member/find_password_success";
		}
	}
	
}
