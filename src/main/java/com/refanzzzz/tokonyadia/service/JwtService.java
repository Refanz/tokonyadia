package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.entity.UserAccount;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String generateAccessToken(UserAccount userAccount);

    String getUserId(String token);

    String extractTokenFromRequest(HttpServletRequest request);

    void blacklistAccessToken(String bearerToken);

    Boolean isTokenBlacklisted(String token);
}
