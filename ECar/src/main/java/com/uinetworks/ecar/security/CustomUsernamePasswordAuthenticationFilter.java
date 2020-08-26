package com.uinetworks.ecar.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.auth.UserPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


// JWT 인증 토큰 발행

@Log4j
@RequiredArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private boolean postOnly = true;
	private HashMap<String, String> jsonRequest;
	private final AuthenticationManager authenticationManager;
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String username = this.obtainUsername(request); 
		String password = this.obtainPassword(request); 
		if (username == null) {
				username = ""; 
			} 
		if (password == null) {
				password = ""; 
			}
		
		log.info("in Authentication:attemptAuthentication:::::");
		username = username.trim();
        UsernamePasswordAuthenticationToken authenticationToken = 
        		new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        log.info("Auth :" + auth);
        return auth;
	}
	
	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String passwordParameter = super.getPasswordParameter();
		if (request.getHeader("Content-Type").equals(ContentType.APPLICATION_JSON.getMimeType())) {
			return jsonRequest.get(passwordParameter);
		}
		return request.getParameter(passwordParameter);
	}
	
	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String usernameParameter = super.getUsernameParameter();
		if (request.getHeader("Content-Type").equals(ContentType.APPLICATION_JSON.getMimeType())) {
			return jsonRequest.get(usernameParameter);
		}
		return request.getParameter(usernameParameter);
	}
	
	/*
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		//Grab principal
		UserPrincipal principal = (UserPrincipal) authResult.getPrincipal();
		
		// Create JWT Token
		String accessToken = createAccessToken(principal., authList)
		
		super.successfulAuthentication(request, response, chain, authResult);
	}	
*/

	/*
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		if (request.getHeader("Content-Type").equals(ContentType.APPLICATION_JSON.getMimeType())) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				this.jsonRequest = mapper.readValue(request.getReader().lines().collect(Collectors.joining()), new TypeReference<Map<String, String>>() { 
					
				});
			} catch (IOException e) {
				e.printStackTrace();
				throw new AuthenticationServiceException("Request Content-Type(application/json) Parsing Error");
			}
		}
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		log.info("[LOGIN_REQUEST] [email: {"+ username + "}, password:" + password + "*******]");

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}*/

	@Override
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}
}
