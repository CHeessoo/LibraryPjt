package com.goodee.library.member;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberDao.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// 아이디 중복 검사
	public boolean isMemberCheck(String m_id) {
		LOGGER.info("[MemberDao] isMemberCheck();");
		
		String sql = "SELECT COUNT(*) FROM tbl_member WHERE m_id = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, m_id); // Integer로 받겠다는 의미
		
		if(result > 0) {  // 아이디가 있으면
			return true;  // true 반환
		} else {          // 아이디가 없으면
			return false; // false 반환
		}
	}
	
	// 회원 정보 추가
	public int insertMember(MemberVo vo) {
		LOGGER.info("[MemberDao] insertMember();");
		
		String sql = "INSERT INTO tbl_member(m_id, m_pw, m_name, m_gender, m_mail, m_phone, m_reg_date, m_mod_date)"
				+ "VALUES(?,?,?,?,?,?,NOW(), NOW())";
		
		List<String> args = new ArrayList<String>();
		args.add(vo.getM_id());
		args.add(vo.getM_pw());
		args.add(vo.getM_name());
		args.add(vo.getM_gender());
		args.add(vo.getM_mail());
		args.add(vo.getM_phone());
		int result = -1;
		jdbcTemplate.update(sql, args.toArray());
		return result;  // insert에 실패하면 -1 반환, 성공하면 업데이트된 수 만큼 반환
	}
	
	
}
