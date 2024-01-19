package com.goodee.library.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberDao.class);
	
	// jdbcTemplate 사용 -> maybatis로 변경
//	@Autowired
//	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// mybatis에서 쿼리를 관리해주는 SqlSession을 의존성 주입한다. (데이터베이스에 접근하는것을 관리)
	@Autowired
	private SqlSession sqlSession;  // 도서쿼리와 회원쿼리를 따로 관리해야하기 때문에 클래스 안에서만 사용하는 접근제한자(private) 이용
	
	// namespace를 전역변수로 설정
	private final String namespace = "com.goodee.library.member.MemberDao.";
	
	// 아이디 중복 검사 - jdbcTemplate
	/*
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
	*/
	
	// 아이디 중복 검사 - mybatis
	public boolean isMemberCheck(String m_id) {
		LOGGER.info("[MemberDao] isMemberCheck();");
		
		// 쿼리 수행 결과를 result로 받아옴
		// selectOne(어떤 쿼리를 실행할건지 적어줌(맵퍼의 namespace.아이디(메소드명이랑 상관X)), 전달하는 값)
		int result = sqlSession.selectOne(namespace + "isMemberCheck", m_id);
		if(result > 0) return true;
		else return false;
	}
	
	// 회원 정보 추가 - jdbcTemplate
	/*
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
	*/
	
	// 회원 정보 추가 - mybatis
	public int insertMember(MemberVo vo) {
		LOGGER.info("[MemberDao] insertMember();");
		
		// 비밀번호 암호화
		// 1. vo안에 pw를 꺼낸다.  vo.setM_pw()
		// 2. 암호화한다.          passwordEncoder.encode()
		// 3. 다시 vo에 넣는다.    vo.getM_pw()
		vo.setM_pw(passwordEncoder.encode(vo.getM_pw()));
		
		int result = -1;
		
		// insert가 됐을경우 result 값을 재할당
		result = sqlSession.insert(namespace + "insertMember", vo);
		return result;
	}
	
	// 로그인 정보 반환 - jdbcTemplate
	// 매개변수로 id, pw를 전달 받은뒤 그에 상응하는 로그인 정보 반환
	/*
	public MemberVo selectMember(MemberVo vo) {
		LOGGER.info("[MemberDao] selectMember();");
		
		String sql = "SELECT * FROM tbl_member WHERE m_id = ?";
		
		// jdbc 템플릿은 List 형태로 정보를 받게 되어있다.
		List<MemberVo> memberVos = new ArrayList<MemberVo>();
		
		try {
			
			// 쿼리 실행 결과를 List로 받아옴
			// RowMapper 란, row 단위로 ResultSet의 row를 매핑하기 위해 JdbcTemplate에서 사용하는 인터페이스
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
	*/
	
	// mybatis
	// 로그인 - 회원정보 조회 및 비밀번호 확인
	public MemberVo selectMember(MemberVo vo) {
		LOGGER.info("[MemberDao] selectMember();");
		
		MemberVo resultVo = new MemberVo();
		resultVo = sqlSession.selectOne(namespace + "selectMember", vo.getM_id());
		
		// 1.   id null 체크 (NullPointerException 방지)
		// 2.   select해온 정보의 비밀번호와 입력받은 비밀번호를 비교
		// 2-1. (입력받은 비밀번호, 암호화된(가져온 정보)비밀번호)
		if(resultVo != null
				&& passwordEncoder.matches(vo.getM_pw(), resultVo.getM_pw()) == false) {  
				resultVo = null;
		}
		return resultVo;
	}
	
	// 회원 목록 - jdbcTemplate
	/*
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
	*/
	
	// 회원 목록 - mybatis
	public List<MemberVo> selectMemberList() {
		LOGGER.info("[MemberDao] selectMemberList();");
		
		List<MemberVo> resultList = new ArrayList<MemberVo>();
		resultList = sqlSession.selectList(namespace + "selectMemberList");
		return resultList;
	}
	
	// 회원 정보 수정
	public int updateMember(MemberVo vo) {
		LOGGER.info("[MemberDao] updateMember();");
		int result = sqlSession.update(namespace + "updateMember", vo);
		return result;
	}
	
	// 회원 단일 정보 데이터베이스에서 조회 (m_no기준)
	public MemberVo selectMemberOne(int m_no) {
		LOGGER.info("[MemberDao] selectMemberOne(int m_no);");
		return sqlSession.selectOne(namespace + "selectMemberOne", m_no);
	}
	
	// 아이디, 이름, 메일 기준 회원 조회 (비밀번호 설정)
	public MemberVo selectMemberOne(MemberVo vo) {
		LOGGER.info("[MemberDao] selectMemberOne(MemberVo vo);");
		MemberVo memberVo = sqlSession.selectOne(namespace + "selectMemberForPassword", vo);
		return memberVo;
	}
	
	// 랜덤 생성한 비밀번호로 데이터베이스 수정
	public int updatePassword(String m_id, String newPassword) {
		LOGGER.info("[MemberDao] updatePassword();");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("m_id", m_id);
		map.put("m_pw", passwordEncoder.encode(newPassword));  // 매개변수로 넘어온 랜덤 비밀번호를 단방향 암호화
		// 데이터베이스에 접근한 경우, 아예 접근하지 못한경우, 접근 했는데 에러가 난 경우를 구분하고자 초기값을 -1로 설정
		int result = -1;
		// 아예 데이터베이스에 접근하지 못할 경우가 생길 수 있기 때문에 그 가능성을 배제하고자 try-catch문 사용
		try {
			result = sqlSession.update(namespace + "updatePassword", map);
		} catch (Exception e) {
			LOGGER.error(e.toString());
		}
		return result;
	}
	
	
}
