package com.uinetworks.ecar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.uinetworks.ecar.model.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;



// CustomAuthenticationProvider 완성본
@Log4j
@Component
public class RestAuthenticationProvider implements AuthenticationProvider {
	@Setter(onMethod_ = @Autowired)
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder pwencoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("MyAuthenticationProvider :::: " + authentication.toString());
		
		String username = authentication.getName();
		if(authentication.getCredentials() == null){
			throw new AuthenticationCredentialsNotFoundException("Credentials is null");
		}

		String password = authentication.getCredentials().toString();
		CustomUser loadedUser = (CustomUser) customUserDetailsService.loadUserByUsername(username); 
		if(loadedUser == null){ 
			throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation"); 
		}
		
		/* checker */ 
		if(!loadedUser.isAccountNonLocked()){ 
			throw new LockedException("User account is locked"); 
		} 
		if(!loadedUser.isEnabled()){ 
			throw new DisabledException("User is disabled"); 
		} 
		
		/* 실질적인 인증 */ 
		if(!pwencoder.matches(password, loadedUser.getPassword())){
			throw new BadCredentialsException("Password does not match stored value"); 
		} 
		/* checker */ 
		if(!loadedUser.isCredentialsNonExpired()){ 
			throw new CredentialsExpiredException("User credentials have expired"); 
		} 
		/* 인증 완료 */ 
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(loadedUser, null, loadedUser.getAuthorities()); 
		result.setDetails(authentication.getDetails()); 
		log.info("Authentication Complete :::: " + result);
		return result;
	}
	@Override
	public boolean supports(Class<?> authentication) {
		log.info("CustomAuthenticationProvider : I can do this");
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//		return true;
	}
}
