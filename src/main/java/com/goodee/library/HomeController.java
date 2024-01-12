package com.goodee.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	
	// 로거
	private static final Logger LOGGER =
			LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value= {"", "/"}, method=RequestMethod.GET)
	public String home() {
		// info 레벨 로그 남기기
		LOGGER.info("[HomeController] home();");
		return "home";
	}
	
}
