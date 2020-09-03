package com.uinetworks.ecar.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class SimpleLoginSuccesshandler extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {
	String defaultTargetUrl = "/v1/loginResult";

	public SimpleLoginSuccesshandler() {
		log.info("No parameter Constructor");
	}
	
	public SimpleLoginSuccesshandler(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
		log.info("parameter Constructor : " + this.defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session == null) {
			return;
		}

		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

}
