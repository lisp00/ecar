package com.uinetworks.ecar.test;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Log4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private boolean postOnly = true;
	
	@Setter(onMethod_= @Autowired)
	private AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		log.info("JWTAuthentication.attemptAuthentication::::::");
		
		if(postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not Supported : " + request.getMethod());
		}
		
		String username = this.obtainUsername(request); 
		String password = this.obtainPassword(request); 
		if (username == null) {
				username = ""; 
			} 
		if (password == null) {
				password = ""; 
			}
		
		username = username.trim();
        UsernamePasswordAuthenticationToken authenticationToken = 
        		new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authenticationToken);
        
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        return auth;
	}

	// 인증 성공시 JWT토큰을 생성하여 response헤더에 붙인다
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		super.successfulAuthentication(request, response, chain, authResult);
	}
}
