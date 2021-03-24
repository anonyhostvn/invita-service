package com.cmc.invitaservice.security;

public class SecurityConstants {
    public static final String SECRET = "BinhLong";
    public static final long EXPIRATION_TIME = 900_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/auth/sign-up";
    public static final String LOGIN_URL = "/auth/login";
    public static final String LOGOUT_URL = "/auth/logout";
    public static final String FORGOT_PASSWORD_URL = "/auth/forgot";
    public static final String RESET_PASSWORD_URL = "/auth/reset";
    public static final String MANAGEMENT_MAIL = "invitacmcapp2021@gmail.com";
    public static final String VERIFY_URL = "/auth/verify";
}
