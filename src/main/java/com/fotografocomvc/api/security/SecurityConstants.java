package com.fotografocomvc.api.security;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {
    @Value("${fotografocomvc.app.jwtSecret}")
    public static String JWT_SECRET;

    @Value("${fotografocomvc.app.jwtExpirationMs}")
    public static Long ACCESS_TOKEN_EXPIRATION;
    @Value("${fotografocomvc.app.jwtRefreshExpirationMs}")
    public static Long REFRESH_TOKEN_EXPIRATION;
}
