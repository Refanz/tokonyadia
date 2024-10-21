package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.LoginRequest;
import com.refanzzzz.tokonyadia.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refreshToken(String token);

    void logout(String accessToken);
}
