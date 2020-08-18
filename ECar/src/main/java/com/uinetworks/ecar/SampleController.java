package com.uinetworks.ecar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

//샘플 컨트롤러
@Log4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {
	@GetMapping("/all")
	public void doAll() {
		log.info("ecar do allcan access everybody");
	}
	
	@GetMapping("/manager")
	public void doMember() {
		log.info("ecar logined manager");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("ecar admin only");
	}
}
