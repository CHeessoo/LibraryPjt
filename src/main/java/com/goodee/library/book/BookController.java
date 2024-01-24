package com.goodee.library.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.goodee.library.util.UploadFileService;

@Controller
@RequestMapping("/book")
public class BookController {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	BookService bookService;
	
	
	// 도서 등록 화면 이동
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createBookForm() {
		LOGGER.info("[BookController] createBookForm();");
		return "book/create";
	}
	
	// 도서 등록 기능
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createBookConfirm(BookVo vo,
			@RequestParam("file") MultipartFile file) {
		LOGGER.info("[BookController] createBookConfirm();");
		int result = -1;
		
		// 1. 파일 파싱(UploadFileService)
		String savedFileName = uploadFileService.upload(file);
		// 2. 도서 등록
		if(savedFileName != null && "".equals(savedFileName) == false) { 
			vo.setB_thumbnail(savedFileName);
			result = bookService.createBookConfirm(vo);
			}
		// 3. 결과 화면 전환
		if(result > 0) {
			return "book/create_success";
		} else {
			return "book/create_fail";
		}
	}
	
	
	// 도서 목록 조회 기능(검색)
	@RequestMapping(method=RequestMethod.GET)
	public String selectBookList(Model model, BookVo vo) {
		LOGGER.info("[BookController] selectBookList();");
		
		// 1. 목록 정보 조회(DB)
		vo.setTotalCount(bookService.selectBookCount(vo.getB_name()));
		List<BookVo> bookVos = bookService.selectBookList(vo);
		// 2. 화면 전환 + 정보 전달
		model.addAttribute("bookVos", bookVos);
		model.addAttribute("pagingVo", vo);
		return "book/listup";
	}
	
	// 도서 상세 이동
	@RequestMapping(value="/{b_no}", method=RequestMethod.GET)
	public String bookDetail(@PathVariable int b_no, Model model) {
		LOGGER.info("[BookController] bookDetail();");
		// 1. 도서 하나 정보 조회
		BookVo vo = bookService.bookDetail(b_no);
		// 2. 화면 전환 + 정보 전달
		model.addAttribute("bookVo", vo);
		return "book/detail";
	}
	
	// 도서 수정 이동
	@RequestMapping(value="/modify/{b_no}", method=RequestMethod.GET)
	public String modifyBookForm(@PathVariable int b_no, Model model) {
		LOGGER.info("[BookController] modifyBookForm();");
		// 1. 기존 정보 조회
		BookVo vo = bookService.bookDetail(b_no);
		// 2. 화면 전환 + 정보 전달
		model.addAttribute("bookVo", vo);
		return "book/modify";
	}
	
	// 도서 수정 기능
	@RequestMapping(value="/modify/{b_no}", method=RequestMethod.POST)
	public String modifyBookConfirm(BookVo vo, @RequestParam("file") MultipartFile file) {
		LOGGER.info("[BookController] mdifyBookConfirm();");
		
		// 1. 만약에 새로운 파일 ㅇ -> 파일 업로드
		if(file.getOriginalFilename().equals("") == false) { // 첨부 파일이 존재하는 경우 (getOriginalFilename 사용 이유 : 부하가 제일 적음)
			String savedFileName = uploadFileService.upload(file);
			if(savedFileName != null) {
				vo.setB_thumbnail(savedFileName);
			}
		}
		// 2. 도서 정보 수정
		int result = bookService.modifyConfirm(vo);
		// 3. 결과 화면 이동
		if(result <= 0) {
			return "book/modify_fail";
		} else {
			return "book/modify_success";
		}
	}
	
	// 도서 삭제 기능

	
}
