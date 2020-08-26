package com.uinetworks.ecar.test;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.extern.log4j.Log4j;

@Log4j
public class JwtAuthorizationFilter /*extends BasicAuthenticationFilter*/ {
	/*

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final boolean debug = this.logger.isDebugEnabled();
		String header = request.getHeader("Authorization");
		

		if (header == null || !header.startsWith("Basic ")) {
			chain.doFilter(request, response);
			return;
		}
		try {
			String[] tokens = extractAndDecodeHeader(header, request);
			assert tokens.length == 2;
			String username = tokens[0];
			if (debug) {
				this.logger.debug("Basic Authentication Authorization header found for user '"+ username + "'");
			}

			if (authenticationIsRequired(username)) {
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
						username, tokens[1]);
				authRequest.setDetails(
						this.authenticationDetailsSource.buildDetails(request));
				Authentication authResult = this.authenticationManager
						.authenticate(authRequest);

				if (debug) {
					this.logger.debug("Authentication success: " + authResult);
				}

				SecurityContextHolder.getContext().setAuthentication(authResult);

				this.rememberMeServices.loginSuccess(request, response, authResult);

				onSuccessfulAuthentication(request, response, authResult);
			}

		}
		catch (AuthenticationException failed) {
			SecurityContextHolder.clearContext();

			if (debug) {
				this.logger.debug("Authentication request for failed: " + failed);
			}

			this.rememberMeServices.loginFail(request, response);

			onUnsuccessfulAuthentication(request, response, failed);

			if (this.ignoreFailure) {
				chain.doFilter(request, response);
			}
			else {
				this.authenticationEntryPoint.commence(request, response, failed);
			}

			return;
		}

		chain.doFilter(request, response);
		super.doFilterInternal(request, response, chain);
	}
*/
}
