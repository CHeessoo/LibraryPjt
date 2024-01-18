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
	
	// 회원 정보 수정
	public int modifyMember(MemberVo vo) {
		LOGGER.info("[MemberService] modifyMember();");
		return dao.updateMember(vo);
	}
	
	// 회원 단일 정보 조회
	public MemberVo getLoginedMemberVo(int m_no) {
		LOGGER.info("[MemberService] getLoginedMemberVo();");
		return dao.selectMemberOne(m_no);
	}
	
	// 비밀번호 설정 기능
	public int findPasswordConfirm(MemberVo vo) {
		LOGGER.info("[MemberService] findPasswordConfirm();");
		// 1. 입력한 정보와 일치하는 사용자가 있는지 확인
		
		MemberVo selectedMember = dao.selectMemberOne(vo);
		int result = 0;
		// 조회한 정보가 존재할 경우
		if(selectedMember != null) {  
			// 2. 새로운 비밀번호 생성
			// 3. 생성된 비밀번호 업데이트
			// 4. 메일 보내기
			
		}
		
		return result;
	}

}
