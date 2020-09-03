package com.uinetworks.ecar.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jdk.nashorn.internal.ir.annotations.Ignore;

@Ignore
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE+1)
@EnableWebSecurity(debug = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final RestAuthenticationEntryPoint authenticationEntryPoint = new RestAuthenticationEntryPoint();
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable();
	        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
	        http.authorizeRequests().antMatchers("/v1/login").permitAll();
	        http.formLogin().disable();
	        
	        
		// 새로구현한 Filter를 UsernamePasswordAuthenticationFilter layer에 삽입
	        http.addFilterAt(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
	 
	 protected CustomUsernamePasswordAuthenticationFilter getAuthenticationFilter() {
	        CustomUsernamePasswordAuthenticationFilter authFilter = new CustomUsernamePasswordAuthenticationFilter();
	        try {
	            authFilter.setFilterProcessesUrl("/v1/login");
	            authFilter.setAuthenticationManager(this.authenticationManagerBean());
	            authFilter.setUsernameParameter("userId");
	            authFilter.setPasswordParameter("userPw");
//	            authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
//	            authFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return authFilter;
	    }
}