package com.goodee.library.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberDao.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
		args.add(passwordEncoder.encode(vo.getM_pw()));
		args.add(vo.getM_name());
		args.add(vo.getM_gender());
		args.add(vo.getM_mail());
		args.add(vo.getM_phone());
		int result = -1;
		result = jdbcTemplate.update(sql, args.toArray());
		return result;  // insert에 실패하면 -1 반환, 성공하면 업데이트된 수 만큼 반환
	}
	
	// 로그인 정보 반환
	// 매개변수로 id, pw를 전달 받은뒤 그에 상응하는 로그인 정보 반환
	public MemberVo selectMember(MemberVo vo) {
		LOGGER.info("[MemberDao] selectMember();");
		
		String sql = "SELECT * FROM tbl_member WHERE m_id = ?";
		
		// jdbc 템플릿은 List 형태로 정보를 받게 되어있다.
		List<MemberVo> memberVos = new ArrayList<MemberVo>();
		
		try {
			
			// 쿼리 실행 결과를 List로 받아옴
			memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() { // row가 여러개인 정보를 받아올건데 그 타입이 MemberVo라는 의미
				// 상속 
				@Override
				public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {  
					// ResultSet rs에 값을 받아서 MemberVo memberVo로 넘긴다.
					MemberVo memberVo = new MemberVo();
					memberVo.setM_no(rs.getInt("m_no"));
					memberVo.setM_id(rs.getString("m_id"));
					memberVo.setM_pw(rs.getString("m_pw"));
					memberVo.setM_name(rs.getString("m_name"));
					memberVo.setM_gender(rs.getString("m_gender"));
					memberVo.setM_mail(rs.getString("m_mail"));
					memberVo.setM_phone(rs.getString("m_phone"));
					memberVo.setM_reg_date(rs.getString("m_reg_date"));
					memberVo.setM_mod_date(rs.getString("m_mod_date"));
					return memberVo;
				}
				
			}, vo.getM_id());
			
			// matches(암호화하지 않은 비밀번호, 데이터베이스에서 가져온 암호화된 비밀번호)를 비교
			if(passwordEncoder.matches(vo.getM_pw(), memberVos.get(0).getM_pw()) == false) {
				memberVos.clear();  // 비교된 두 비밀번호가 일치하지 않는다면 meberVos(가져온 정보)를 clear한다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 조회된 정보가 존재하면 해당 정보를 반환하고 아니라면 null을 반환
		return memberVos.size() > 0 ? memberVos.get(0) : null;
	}
	
	// 회원 목록
	public List<MemberVo> selectMemberList() {
		LOGGER.info("[MemberDao] selectMemberList();");
		
		String sql = "SELECT * FROM tbl_member";
		List<MemberVo> memberVos = new ArrayList<MemberVo>();
		// 쿼리 실행 결과를 List로 받아옴
		memberVos = jdbcTemplate.query(sql, new RowMapper<MemberVo>() { 
			// 상속 
			@Override
			public MemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {  
				// ResultSet rs에 값을 받아서 MemberVo memberVo로 넘긴다.
				MemberVo memberVo = new MemberVo();
				memberVo.setM_no(rs.getInt("m_no"));
				memberVo.setM_id(rs.getString("m_id"));
				memberVo.setM_pw(rs.getString("m_pw"));
				memberVo.setM_name(rs.getString("m_name"));
				memberVo.setM_gender(rs.getString("m_gender"));
				memberVo.setM_mail(rs.getString("m_mail"));
				memberVo.setM_phone(rs.getString("m_phone"));
				memberVo.setM_reg_date(rs.getString("m_reg_date"));
				memberVo.setM_mod_date(rs.getString("m_mod_date"));
				return memberVo;
			}
		});

		return memberVos;
	}
	
	
}
