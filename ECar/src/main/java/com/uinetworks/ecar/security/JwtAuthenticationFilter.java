package com.uinetworks.ecar.security;

import java.io.IOException;

import java.text.MessageFormat;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//Before UsernamePasswordAuthenticationFilter
@Component
@Log4j
public class JwtAuthenticationFilter extends GenericFilterBean {
	@Setter(onMethod_= @Autowired)
    private JwtTokenProvider jwtTokenProvider;

	@Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest)req);
        if(token != null) {
		    log.info("AccessTokenCheckAuthenticationFilter -> token found: " + token);
		    if(jwtTokenProvider.validateToken(token) != false) {
		    	log.info("AccessTokenCheckAuthenticationFilter -> token validated : " + token);
		    	Authentication validatedAuthenticationToken = jwtTokenProvider.getAuthentication(token);
		    	if(validatedAuthenticationToken == null) 
		    		throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
		    } else {
		    	log.info("AccessTokenCheckAuthenticationFilter -> token is not validated : " + token);
		    }
        } else {
        	log.info("AccessTokenCheckAuthenticationFilter -> token null");
        }
        chain.doFilter(req, res);
    }
}