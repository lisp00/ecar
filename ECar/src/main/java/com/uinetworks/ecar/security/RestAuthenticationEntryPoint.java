package com.uinetworks.ecar.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import lombok.extern.log4j.Log4j;

@Log4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException ) throws IOException, ServletException {
		String contentType = request.getContentType();
		log.info(contentType);
		response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );
	}
}
