package com.fotografocomvc.api.security;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {
    public static String JWT_SECRET = "eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY5NDY0MzE4MywiaWF0IjoxNjk0NjQzMTgzfQ";
    public static Long ACCESS_TOKEN_EXPIRATION = 300000L;
    public static Long REFRESH_TOKEN_EXPIRATION = 600000L;
}
