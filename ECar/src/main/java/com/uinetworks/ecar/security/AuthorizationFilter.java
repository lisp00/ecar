package com.uinetworks.ecar.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.uinetworks.ecar.model.CustomUser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class AuthorizationFilter extends GenericFilterBean {
	@Setter(onMethod_= @Autowired)
	private CustomUserDetailsService customUserDetailsService;
	
	@Setter(onMethod_= @Autowired)
	private JwtTokenProvider jwtTokenProvider; 
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String token = req.getHeader("Authorization");
//		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) { // Bearer 토큰 구분
		if(token == null) {
			log.info("AuthorizationFilter -> doFilter -> token " + token);
		}
		if(jwtTokenProvider.validateToken(token)!= false) {
			Authentication authentication = getUsernamePasswordAuthentication((HttpServletRequest)request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.info("Token is validated");
			log.info("SecurityContextHolder.Context.Authentication : " + SecurityContextHolder.getContext().getAuthentication());	
		} else if(req.getParameter("userId") != null) {
			Authentication authentication = getUsernamePasswordAuthentication((HttpServletRequest)request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.info("Token is null");
			log.info("SecurityContextHolder.Context.Authentication : " + SecurityContextHolder.getContext().getAuthentication());
		}
		chain.doFilter(request, response);
	}	

	private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING);
        if(token != null){
            // parse the token and validate it (decode)
            String username = jwtTokenProvider.getUserId(token);

            if(username != null){
                CustomUser user = (CustomUser) customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
                log.info("JwtAuthorizationFilter -> getUsernamePasswordAuthentication() : " + auth);
                return auth;
            }
            return null;
        }
        return null;
	}
}
