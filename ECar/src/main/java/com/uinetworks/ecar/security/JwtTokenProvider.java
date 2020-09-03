package com.uinetworks.ecar.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.uinetworks.ecar.model.CustomUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private String secretKey = JwtProperties.SECRET_KEY;

    // 토큰 유효시간 15일
    private long tokenValidTime = 15 * 24 * 60 * 60 * 1000L;
    
    @Setter(onMethod_= @Autowired)
    private CustomUserDetailsService customUserDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
    	log.info("JwtTokenProvider init()");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT 토큰 생성
    public String createToken(String userId, List<? extends GrantedAuthority> authList) {
		Map<String, Object> headers = new HashMap<>();    
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");
		
        Claims claims = Jwts.claims().setSubject(userId); // JWT payload 에 저장되는 정보단위
        claims.put("data", "hello");
        claims.put("auth", authList); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();
        
        String jwtToken = Jwts.builder()
        		.setHeader(headers)
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 
                                                                // signature 에 들어갈 secret값 세팅
                .compact();
        log.info("createToken() -> JWTTokenValue : " + jwtToken);
        return jwtToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
    	if(token == null) {
    		log.info("getAuthentication() -> getuserid() -> token value : " + token);
    		return null;
    	}
        CustomUser userDetails = (CustomUser) customUserDetailsService.loadUserByUsername(this.getUserId(token));
        log.info("getAutentication -> UserDetails : " + userDetails);
        UsernamePasswordAuthenticationToken authenticationToken = 
        		new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), new ArrayList<GrantedAuthority>());
        log.info("getAuthentication -> authenticationToken : " + authenticationToken);
        return authenticationToken;
    }

    // JWT 토큰에서 인가 정보 조회
    public Authentication getAuthorization(String token) {
    	if(token == null) {
    		log.info("getAuthorization() -> getuserid() -> token value : " + token);
    		return null;
    	}
    	CustomUser userDetails = (CustomUser) customUserDetailsService.loadUserByUsername(this.getUserId(token));
    	log.info("getAuthorization -> UserDetails : " + userDetails);
    	UsernamePasswordAuthenticationToken authorizationToken = 
    			new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    	log.info("getAuthorization -> authorizationToken : " + authorizationToken);
    	return authorizationToken;
    }

    // 토큰에서 회원 정보 추출
    public String getUserId(String token) {
    	if(token == null) {
    		log.info("getUserId() -> token null");
    		return null;
    	}
    	log.info("getUserId() -> token value : " + token);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    
    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
    	String token = request.getHeader("AccessToken");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.TOKEN_PREFIX)) {
//            return bearerToken.substring(7, bearerToken.length());
//        }
    	log.info("resolveToken() -> Authorization value : " + token);
        return token;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
    	log.info("validateToken() : " + jwtToken);
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            log.info("validateToken() -> Token Validation : " + claims);
            boolean validateReturn = claims.getBody().getExpiration().before(new Date());
            log.info("validateToken() -> expiration date : " + claims.getBody().getExpiration());
            log.info("validateToken() -> TokenValidate : " + !validateReturn);
            return !validateReturn;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
        	log.error("Invalid JWT token");
        } catch (UnsupportedJwtException ex) {
        	log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	log.error("JWT claims string is empty.");
        } catch (Exception e) {
        	log.error("Invalid JWT token" + e);
        }
        return false;
    }
}