package com.chardin.backenddelivery.websecurity;

public class SecurityContants {
	public static final String SECRET = "f@ther";
	public static final long EXPIRATION_TIME = 900_000; // 15 mins
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_NAME = "Authorization";
	public static final String LOGIN_URL = "/login";
}
