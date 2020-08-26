package com.uinetworks.ecar.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uinetworks.ecar.test.CustomAuthenticationProvider;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j

public class authTest {
	/*
	@Autowired
	private static AuthenticationManager am = new CustomAuthenticationProvider();

	@Test
	public void testClass() {
		
		String username = "secu";
		String password = "1234";
		
		Authentication request = new UsernamePasswordAuthenticationToken(username, password);
		Authentication result = am.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(result);
		log.info("Successfully authenticated : " + SecurityContextHolder.getContext().getAuthentication());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			log.info("principal name : " + ((UserDetails) principal).getUsername());
		} else {
			log.info("principal name else : " + principal.toString());
		}
	}
	*/
}
