package com.fotografocomvc.api.security;

public class SecurityConstants {
    public static final Long JWT_EXPIRATION = 1000L * 60 * 10;
    public static final String JWT_SECRET = "secret";

    public static final Long REFRESH_TOKEN_EXPIRATION = 1000L * 10;
}
