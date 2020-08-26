package com.uinetworks.ecar;
import lombok.Data;

@Data
public class JwtProperties {
	public static final String SECRET_KEY = "@ECar2020-08-24!@";
	public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 15; // 15일
	public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
