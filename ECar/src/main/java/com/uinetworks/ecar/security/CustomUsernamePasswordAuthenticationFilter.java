package com.uinetworks.ecar.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project: 
 * DATE: 2019-10-18
 * AUTHOR: JohnMark (Chang Jeong Hyeon)
 * EMAIL: practice1356@gmail.com
 * DESCRIPTION: CustomUsernamePasswordAuthenticationFilter for Accepting json or form content type request
 */
@Slf4j
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private boolean postOnly = true;
    private Map<String, String> jsonRequest;
    
    @Setter(onMethod_= @Autowired)
    private JwtTokenProvider jwtTokenProvider;
    
    @Setter(onMethod_= @Autowired)
    private RestFulAuthentication restfulAuthentication;
    
    @Override
    protected String obtainPassword(HttpServletRequest request) {
    	super.setPasswordParameter("userPw");
        String passwordParameter = super.getPasswordParameter();
        if (request.getHeader("Content-Type").equals(ContentType.APPLICATION_JSON.getMimeType())) {
            return jsonRequest.get(passwordParameter);
        }
        return request.getParameter(passwordParameter);
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
    	super.setUsernameParameter("userId");
        String usernameParameter = super.getUsernameParameter();
        if (request.getHeader("Content-Type").equals(ContentType.APPLICATION_JSON.getMimeType())) {
            return jsonRequest.get(usernameParameter);
        }
        return request.getParameter(usernameParameter);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	log.info("in attemptauthentication");
    	String session = null;
    	String token = jwtTokenProvider.resolveToken(request);
    	
    	jsonParser(request);
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        
        Authentication authentication = null;

        if (username == null) {username = "";}
        if (password == null) {password = "";}
        username = username.trim();
        log.info("[LOGIN_REQUEST] [userId: {" + username + "}, password: " + password + "]");
        
        ////////////////////////Header Type////////////////////////
        if(request.getHeader("Content-Type").equals(ContentType.APPLICATION_JSON.getMimeType())) {
        	// REST 방식 // 토큰 유무
        	if(token != null) {
        		authentication = restfulAuthentication.authenticationCheckByToken(token);
        		if(authentication != null) {
        			username = authentication.getName();
        			password = (String) authentication.getCredentials();
        		} 
        	}
        }
    	
    		
    		

    	//////////////////////////////////////////////////////
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    public void jsonParser(HttpServletRequest request) {
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
    }

    @Override
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}