package com.uinetworks.ecar.security;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.uinetworks.ecar.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j;

@Log4j
public class TokenHandler {
	BCryptPasswordEncoder encoder;
	
	
	public String createToken(Authentication authentication) {
		String username = (String) authentication.getPrincipal();
				
		return null;		
	}
	
	
	public String createToken() {
	    List authList = new ArrayList();
	    authList.add("ROLE_USER");
	    authList.add("ROLE_MANAGER");
	    authList.add("ROLE_ADMIN");
	    
		Map<String, Object> headers = new HashMap<>();    
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		Map<String, Object> payloads = new HashMap<>();
		Long expiredTime = 1000 * 60l; // 만료기간 1분    
		Date now = new Date();
		now.setTime(now.getTime() + expiredTime);    
		payloads.put("exp", now);
		payloads.put("data", "hello world!");
		payloads.put("auth", authList);

		String jwt = Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET_KEY.getBytes())
				.compact();
		log.info("JWT : " + jwt);
		return jwt;   
	}

	public static void getTokenFromJwtString(String jwtTokenString) throws InterruptedException {
		Claims claims = Jwts.parser()
				.setSigningKey(JwtProperties.SECRET_KEY.getBytes())
				.parseClaimsJws(jwtTokenString)
				.getBody();
	    Object expiration = claims.get("exp");
        System.out.println(expiration);
        Object data = claims.get("data");
        System.out.println(data);
        Object auth = claims.get("auth");
        System.out.println(auth);
	}
	
	public void test2() throws InterruptedException {
		getTokenFromJwtString(createToken());
		log.info("success");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void test() throws UnsupportedEncodingException {
	    List authList = new ArrayList();
	    authList.add("ROLE_USER");
	    authList.add("ROLE_MANAGER");
	    authList.add("ROLE_ADMIN");
	    
	    String jwt = Jwts.builder()
	            .setIssuer("Stormpath")
	            .setSubject("msilverman")
	            .claim("scope", authList)
	            .claim("name", "Micah Silverman")
	            .setIssuedAt(Date.from(Instant.now()))
	            .setExpiration(Date.from(Instant.now().plus(14, ChronoUnit.DAYS)))
	            .signWith(SignatureAlgorithm.HS256,
	            		JwtProperties.SECRET_KEY.getBytes("UTF-8"))
	            .compact();
	     
	    log.info("jwt : " + jwt);
	     
	    Claims claims =
	            Jwts.parser().setSigningKey(JwtProperties.SECRET_KEY.getBytes("UTF-8")).parseClaimsJws(jwt).getBody();
	     
	    log.info("claims : " + claims);
	    log.info("claims.scope : " + claims.get("scope"));
	    log.info("claims.name: " + claims.get("name"));
	}	
}
