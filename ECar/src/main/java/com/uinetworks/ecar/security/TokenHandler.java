package com.uinetworks.ecar.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j;

@Log4j
public class TokenHandler {
	private static String key = "k2dks7d8KDiw28rdkgjhIOAoi209PQPPP1kid8NVJDJIIJenjneuduwjngdhiushuinjcnmjzhUUHUH328";

	public String createToken() {

		Map<String, Object> headers = new HashMap<>();    
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		Map<String, Object> payloads = new HashMap<>();
		Long expiredTime = 1000 * 60l; // 만료기간 1분    
		Date now = new Date();
		now.setTime(now.getTime() + expiredTime);    
		payloads.put("exp", now);
		payloads.put("data", "hello world!");

		String jwt = Jwts.builder()
				.setHeader(headers)
				.setClaims(payloads)
				.signWith(SignatureAlgorithm.HS256, key.getBytes())
				.compact();
		log.info("JWT : " + jwt);
		return jwt;   
	}

	public static void getTokenFromJwtString(String jwtTokenString) throws InterruptedException {
		Claims claims = Jwts.parser()
				.setSigningKey(key.getBytes())
				.parseClaimsJws(jwtTokenString)
				.getBody();
	    Object expiration = claims.get("exp");
        System.out.println(expiration);
        Object data = claims.get("data");
        System.out.println(data);
	}
}
