package com.uinetworks.ecar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uinetworks.ecar.model.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class RestFulAuthentication {
	@Setter(onMethod_= @Autowired)
	private CustomUserDetailsService customUserDetailsService;

	@Setter(onMethod_= @Autowired)
	private BCryptPasswordEncoder encoder;

	@Setter(onMethod_= @Autowired)
	private JwtTokenProvider jwtTokenProvider;

	public Authentication authenticationCheckById(String userId, String userPw) {
		CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername(userId);
		if(encoder.matches(userPw, user.getPassword())) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, userPw);
			return authentication;
		}
		else {
			log.info("User Password invalid");
			return null;
		}
	}

	public Authentication authenticationCheckByToken(String token) {
		Authentication authentication = null;
		if(jwtTokenProvider.validateToken(token)) {
			authentication = jwtTokenProvider.getAuthentication(token);
			if(authentication != null) {
				return authentication;
			} else {
				log.info("Authentication getting failed");
				return null;
			}
		} else {
			log.info("Token is invalited");
			return null;
		}
	}
	
	public Authentication authorizationCheck(Authentication authentication) {
		CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername(authentication.getName());
		if(user == null) {
			log.info("Not authorized user");
			return null;
		} else {
			UsernamePasswordAuthenticationToken authorizationToken = 
					new UsernamePasswordAuthenticationToken(
							authentication.getName(), authentication.getCredentials(), authentication.getAuthorities());
			return authorizationToken;
		}
	}
}
