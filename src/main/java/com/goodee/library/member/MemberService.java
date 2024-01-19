package com.goodee.library.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;  // 메일을 전송해주는 기능
	
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
			String newPassword = createNewPassword();
		// 3. 생성된 비밀번호 업데이트
			result = dao.updatePassword(vo.getM_id(), newPassword);
			if(result > 0) {
				// 4. 메일 보내기
				sendNewPasswordByMail(vo.getM_mail(), newPassword);
			}
		}
		return result;
	}
	
	// 메일로 비밀번호 보내기
	private void sendNewPasswordByMail(String toMailAddr, String newPw) {
		LOGGER.info("[MemberService] sendNewPasswordByMail();");
		
		// 톰캣 9 ver, javax-mail 1.6.2ver (톰캣 10부턴 모듈 받아올 때 jakarta명 사용)
		
		final MimeMessagePreparator mime = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				// mimeMessage를 메일 보낼 때 사용, HTML 형식으로 보낼지? true, 인코딩 형식
				final MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				// 메일 규격 세팅
				mimeHelper.setTo(toMailAddr);                                        // 메일을 보낼 주소
				mimeHelper.setSubject("[구디 도서관] 새로운 비밀번호 안내입니다.");  // 메일 제목 설정
				mimeHelper.setText("새 비밀번호: " + newPw, true);                   // 메일 내용(비밀번호) + HTML로 보낼건지를 true로 설정
			}
		};
		// 메일 전송
		javaMailSenderImpl.send(mime);
	}
	
	// 비밀번호 생성
	private String createNewPassword() {
		LOGGER.info("[MemberService] createNewPassword();");
		// char 배열 선언
	    char[] chars = new char[] {
		          '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				  'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
				  'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
				  'u', 'v', 'w', 'x', 'y', 'z'
				  };
	   
	    StringBuffer sb = new StringBuffer();
	    SecureRandom sr = new SecureRandom();  // 랜덤 객체가 가진 의사 난수에 규칙이 있기 때문에 그를 보완한 SecureRandom 사용
	    sr.setSeed(new Date().getTime());      // 현재 시간을 자원(seed)로 사용해서 랜덤한 값을 생성
	    int index = 0;                         // 인덱스 초기값 생성
	    int length = chars.length;             // 배열의 길이
	    for(int i = 0; i < 8; i++) {           // 총 8번 반복
		    index = sr.nextInt(length);
		    if(index % 2 == 0) {
			    // sb.append(chars[index]);
			    // sb.append(String.valueOf(chars[index]));
			    sb.append(String.valueOf(chars[index]).toUpperCase());  // chars[index] 값을 String 타입으로 바꾸고 대문자로 변환
		    } else {
		    	sb.append(String.valueOf(chars[index]).toLowerCase());  // 소문자 변환
		    }
	    }
	   return sb.toString();
	}

}









