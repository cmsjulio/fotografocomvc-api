package com.fotografocomvc.api.security;

public class SecurityConstants {
    // TODO ajustar tempos e alterar secret
    public static final Long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 5;
    public static final String JWT_SECRET = "secret";
    public static final Long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 10;
}
