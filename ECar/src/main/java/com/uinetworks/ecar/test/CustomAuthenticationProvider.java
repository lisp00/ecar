package com.uinetworks.ecar.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.uinetworks.ecar.model.MemberVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAuthenticationProvider implements AuthenticationManager {
	private UserDetailsService userDetailsService;
    private PasswordEncoder encoder;
    
    public CustomAuthenticationProvider(UserDetailsService userDetailsService,PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("CustomUserAuthenticationProvider.authenticate :::: {}" + authentication.toString());
        
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        
        String userId = token.getName();
        
        MemberVO memberVO = null;
        
        if(!StringUtils.isEmpty(userId)) {
        	memberVO = (MemberVO) userDetailsService.loadUserByUsername(userId);
        }
        
        if(ObjectUtils.isEmpty(memberVO)) {
            throw new UsernameNotFoundException("Invalid username");
        }
        
        String password = memberVO.getPassword();
        
        if(password.equals(encoder.encode(String.valueOf(token.getCredentials())))) {
            throw new BadCredentialsException("Invalid password");
        }
        
        return new UsernamePasswordAuthenticationToken(memberVO, password, (Collection<? extends GrantedAuthority>) memberVO.getAuthList());
        
    }
 
}
	/*
	static final List AUTHORITIES = new ArrayList();
	
	ProviderManager pm = new ProviderManager(AUTHORITIES);
	
	static {
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		log.info("in authenticate");
		if(auth.getCredentials().equals("1234")) {
			return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
		}
		throw new BadCredentialsException("Bad Credentials");
	}
	*/
