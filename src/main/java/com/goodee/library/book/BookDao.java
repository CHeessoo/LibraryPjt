package com.goodee.library.book;

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

	public int insertBook(BookVo vo) {
		LOGGER.info("[BookDao] insertBook();");
		return sqlSession.insert(namespace + "insertBook", vo);
	}
	
}
