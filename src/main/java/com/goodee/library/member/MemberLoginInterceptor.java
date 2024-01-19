package com.goodee.library.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MemberLoginInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest req,
			HttpServletResponse resp, Object handler) throws Exception{
		// 현재 세션에 담겨있는 로그인 정보 가져온 뒤 - 정보 유무 확인 - 로그인 정보가 세션에 없으면 리다이렉트 진행
		
		HttpSession session = req.getSession();  // 세션 정보 가져오기
		if(session != null) {                    // 세션 정보가 존재할 경우
			Object obj = session.getAttribute("loginMember");
			if(obj != null) {                    // 세션에 loginMember 정보가 존재할 경우                   
				return true;                     // 원래 넘어가야 하는 페이지로 이동
			} 
		}
		// Session,Object가 null 인 경우
		resp.sendRedirect(req.getContextPath() + "/member/login");
		return false;                            // 로그인 페이지로 이동
		
	}

}
