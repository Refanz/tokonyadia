package com.refanzzzz.tokonyadia.util;

public class JwtUtil {
    public static String parseJwtToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
