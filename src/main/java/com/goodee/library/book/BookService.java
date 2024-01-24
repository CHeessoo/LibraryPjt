package com.goodee.library.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	BookDao bookDao;
	
	// 도서 등록 기능
	public int createBookConfirm(BookVo vo) {
		LOGGER.info("[BookService] createBookConfirm();");
		return bookDao.insertBook(vo);
	}
	
	// 도서 목록 조회 기능
	public List<BookVo> selectBookList(BookVo vo) {
		LOGGER.info("[BookService] selelctBookList();");
		return bookDao.selectBookList(vo);
	}
	
	// 조회 도서 총 개수
	public int selectBookCount(String b_name) {
		LOGGER.info("[BookService] selectBookCount();");
		int totalCount = bookDao.selectBookCount(b_name);
		return totalCount;
	}
	
	// 도서 상세 이동
	public BookVo bookDetail(int b_no) {
		LOGGER.info("[BookService] bookDetail();");
		BookVo vo = bookDao.selectBookOne(b_no);
		return vo;
	}
	
	// 도서 수정
	public int modifyConfirm(BookVo vo) {
		LOGGER.info("[BookService] modifyConfirm();");
		return bookDao.updateBook(vo);
	}

}
