package com.uinetworks.ecar.security;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j;

@Log4j
public class TokenHandler2 {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void test() throws UnsupportedEncodingException {
	    List authList = new ArrayList();
	    authList.add("manager");
	    authList.add("admin");
	    authList.add("user");
	     
	     
	    String jwt = Jwts.builder()
	            .setIssuer("Stormpath")
	            .setSubject("msilverman")
	            .claim("scope", authList)
	            .claim("name", "Micah Silverman")
	            .setIssuedAt(Date.from(Instant.now()))
	            .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
	            .signWith(SignatureAlgorithm.HS256,
	                       "secret".getBytes("UTF-8"))
	            .compact();
	     
	    log.info("jwt : " + jwt);
	     
	    Claims claims =
	            Jwts.parser().setSigningKey("secret".getBytes("UTF-8")).parseClaimsJws(jwt).getBody();
	     
	    log.info("claims : " + claims);
	    log.info("claims.scope : " + claims.get("scope"));
	    log.info("claims.name: " + claims.get("name"));
	}
}
