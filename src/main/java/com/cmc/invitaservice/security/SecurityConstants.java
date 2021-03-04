package com.cmc.invitaservice.security;

public class SecurityConstants {
    public static final String SECRET = "BinhLong";
    public static final long EXPIRATION_TIME = 900_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";
    public static final String LOGIN_URL = "/user/login";
}
