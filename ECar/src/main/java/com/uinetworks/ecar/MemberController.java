package com.uinetworks.ecar;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uinetworks.ecar.model.AuthVO;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/member")
@RestController
public class MemberController {
	
	@GetMapping(value = "/register", produces = "text/plan; charset=UTF-8")
	public String register() {
		return "안녕하세요";
	}
	
	@GetMapping(value = "/getjson/{val}/{pid}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String[] getJson(@PathVariable("val") String val, @PathVariable("pid") String pid) {
		return new String[] {"n1 : " + val, "n2 : " + pid};
	}
	
	@PostMapping(value = "/getauth", produces = {MediaType.APPLICATION_JSON_VALUE})
	public AuthVO convert(@RequestBody AuthVO auth) {
		return auth;
	}

}
