package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.UserRole;
import com.refanzzzz.tokonyadia.dto.request.LoginRequest;
import com.refanzzzz.tokonyadia.dto.response.LoginResponse;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import com.refanzzzz.tokonyadia.service.AuthService;
import com.refanzzzz.tokonyadia.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//        );

//            UserAccount userAccount = (UserAccount) authentication.getPrincipal();

        UserAccount userAccount = UserAccount.builder().username("refanzzzz").role(UserRole.ROLE_ADMIN).build();

        String accessToken = jwtService.generateAccessToken(userAccount);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .role(userAccount.getRole().getDescription())
                .build();
    }
}
