package com.uinetworks.ecar.test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.uinetworks.ecar.JwtProperties;
import com.uinetworks.ecar.model.MemberVO;
import com.uinetworks.ecar.security.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
/*
@Log4j
@Component
public class JwtTokenProvider {
    @Setter(onMethod_= @Autowired)
    private CustomUserDetailsService customUserDetailsService;
    
    @Setter(onMethod_= @Autowired)
    private MemberVO memberVo;
    
 
    public String generateToken(Authentication authentication) {
 
        String userPrincipal = (String) authentication.getPrincipal();
        
        memberVo = (MemberVO) customUserDetailsService.loadUserByUsername(userPrincipal);
 
        memberVo.getAuthList();
        
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
 
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JwtProperties.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
 
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JwtProperties.SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
        	log.error("Invalid JWT token");
        } /*catch (ExpiredJwtException ex) {
        	log.error("Expired JWT token");
        }*//* catch (UnsupportedJwtException ex) {
        	log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	log.error("JWT claims string is empty.");
        }
        return false;
    }
    
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
*/