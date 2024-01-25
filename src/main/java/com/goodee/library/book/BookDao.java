package com.goodee.library.book;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookDao.class);
	
	private final String namespace = "com.goodee.library.book.BookDao.";
	
	@Autowired
	private SqlSession sqlSession;

	
	// 도서 등록 기능
	public int insertBook(BookVo vo) {
		LOGGER.info("[BookDao] insertBook();");
		return sqlSession.insert(namespace + "insertBook", vo);
	}
	
	// 도서 목록 조회 
	public List<BookVo> selectBookList(BookVo vo) {
		LOGGER.info("[BookDao] selectBookList();");
		
		List<BookVo> bookVos = sqlSession.selectList(namespace + "selectBookList", vo);
		return bookVos;
	}
	
	// 조회 도서 총 개수
	public int selectBookCount(String b_name) {
		LOGGER.info("[BookDao] selectBookCount();");
		int totalCount = sqlSession.selectOne(namespace + "selectBookCount", b_name);
		return totalCount;
	}
	
	// 도서 상세 조회
	public BookVo selectBookOne(int b_no) {
		LOGGER.info("[BookDao] selectBookOne();");
		BookVo vo = sqlSession.selectOne(namespace + "selectBookOne", b_no);
		return vo;
	}
	
	// 도서 수정
	public int updateBook(BookVo vo) {
		LOGGER.info("[BookDao] updateBook();");
		int result = sqlSession.update(namespace + "updateBook", vo);
		return result;
	}
	
	// 도서 삭제
	public int deleteBook(int b_no) {
		LOGGER.info("[BookDao] deleteBook();");
		int result = sqlSession.delete(namespace + "deleteBook", b_no);
		return result;
	}
	
}
