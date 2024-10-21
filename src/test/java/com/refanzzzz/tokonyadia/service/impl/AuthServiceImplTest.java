package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.UserRole;
import com.refanzzzz.tokonyadia.dto.request.LoginRequest;
import com.refanzzzz.tokonyadia.dto.response.AuthResponse;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import com.refanzzzz.tokonyadia.service.JwtService;
import com.refanzzzz.tokonyadia.service.RefreshTokenService;
import com.refanzzzz.tokonyadia.service.UserAccountService;
import com.refanzzzz.tokonyadia.util.AuthValidationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserAccountService userAccountService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthValidationUtil authValidationUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldReturnLoginResponseWhenLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("refanzzzz");
        loginRequest.setPassword("123");

        Mockito.doNothing().when(authValidationUtil).validate(loginRequest);

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authentication);

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("refanzzzz");
        userAccount.setPassword("123");
        userAccount.setRole(UserRole.ROLE_ADMIN);
        Mockito.when(authentication.getPrincipal()).thenReturn(userAccount);

        String accessToken = "ueh3d3d3";
        Mockito.when(jwtService.generateAccessToken(Mockito.any())).thenReturn(accessToken);

        String refreshToken = "od3odhid3d3";
        Mockito.when(refreshTokenService.createToken(Mockito.any())).thenReturn(refreshToken);

        AuthResponse authResponseExpect = new AuthResponse();
        authResponseExpect.setAccessToken(accessToken);
        authResponseExpect.setRefreshToken(refreshToken);
        authResponseExpect.setRole(userAccount.getRole().getDescription());

        AuthResponse authResponseActual = authService.login(loginRequest);

        Assertions.assertEquals(authResponseActual.getRefreshToken(), authResponseExpect.getRefreshToken());
        Assertions.assertEquals(authResponseActual.getAccessToken(), authResponseExpect.getAccessToken());
        Assertions.assertEquals(authResponseActual.getRole(), authResponseExpect.getRole());
    }
}