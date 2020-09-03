package com.uinetworks.ecar.security;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uinetworks.ecar.model.CustomUser;
import com.uinetworks.ecar.model.MemberVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class TokenTest {
	
	@Setter(onMethod_ = @Autowired)
	CustomUserDetailsService customUserDetailsService;
	
	@Setter(onMethod_ = @Autowired)
	JwtTokenProvider tokenProvider;
	
//	@Test
	public void createToken() throws UnsupportedEncodingException, InterruptedException {
		MemberVO memberVO = new MemberVO();
		
		CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername("admin");
		
		// Collection -> Arraylist 변환
		Collection<? extends GrantedAuthority> authoritiesCollection = new HashSet<>(user.getAuthorities());
		ArrayList<? extends GrantedAuthority> authoritiesList = new ArrayList<>(authoritiesCollection);
		
		String token = tokenProvider.createToken(user.getUsername(), authoritiesList);
		log.info("Token Value : " + token);
	}
	
//	@Test
	public void contextHolderSetNull() {
		SecurityContextHolder.getContext().setAuthentication(null);
		log.info(SecurityContextHolder.getContext().getAuthentication());
	}
	
}
