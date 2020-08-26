package com.uinetworks.ecar.security;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.uinetworks.ecar.JwtProperties;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class TokenTest {
//	@Test
	public void testTokenCreate() {
		TokenHandler token = new TokenHandler();
		token.createToken();
	}
	
//	@Test
	public void testTokenInvalid() throws InterruptedException {
		String key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiaGVsbG8gd29ybGQhIiwiZXhwIjoxNTk4MTgzNTMzMTc4fQ.xJG-VWwdabDGI9TyFr80aRumejMRAOPUHCHLNSSvhRc";
		TokenHandler token = new TokenHandler();
		token.getTokenFromJwtString(key);
		
	}
	
	@Test
	public void testToken() throws UnsupportedEncodingException, InterruptedException {
		TokenHandler tokenHandler = new TokenHandler();
		tokenHandler.test2();
	}
}
