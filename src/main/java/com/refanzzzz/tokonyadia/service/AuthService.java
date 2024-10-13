package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.LoginRequest;
import com.refanzzzz.tokonyadia.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
