package com.uinetworks.ecar.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uinetworks.ecar.model.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class RestLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler { 
	private RequestCache requestCache = new HttpSessionRequestCache();
	private Map<String, String> jsonRequest;
	
	@Setter(onMethod_ = @Autowired)
	private JwtTokenProvider jwtTokenProvider;
	
	@Setter(onMethod_ = @Autowired)
	private BCryptPasswordEncoder encoder;
	
	@Setter(onMethod_ = @Autowired)
	private CustomUserDetailsService customUserDetailsService;
	
	@Override 
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		log.info("RestLoginSuccessHandler -> onAuthenticationSuccess()");
		handle(request, response, authentication); 
		clearAuthenticationAttributes(request);
	} 
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
			throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response); if (savedRequest == null) { 
			clearAuthenticationAttributes(request); 
			return; 
		}
		String targetUrlParam = getTargetUrlParameter(); 
		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
			requestCache.removeRequest(request, response); 
			clearAuthenticationAttributes(request); 
			return; 
		} 
		clearAuthenticationAttributes(request); 
	} 
}

