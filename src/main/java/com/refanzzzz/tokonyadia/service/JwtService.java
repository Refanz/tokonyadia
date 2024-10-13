package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.entity.UserAccount;

public interface JwtService {
    String generateAccessToken(UserAccount userAccount);

    String getUserId(String token);
}
