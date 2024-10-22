package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.UserRole;
import com.refanzzzz.tokonyadia.dto.request.auth.LoginRequest;
import com.refanzzzz.tokonyadia.dto.response.auth.AuthResponse;
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

    @Test
    void shouldReturnAuthResponseWhenRefreshToken() {
        String token = "euhfehefef";

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("diii3d");
        userAccount.setPassword("123");
        userAccount.setRole(UserRole.ROLE_CUSTOMER);

        String newRefreshToken = "ded3d3d3";

        String newToken = "e22pd2d";

        Mockito.when(refreshTokenService.getUserIdByToken(Mockito.any())).thenReturn(token);
        Mockito.when(userAccountService.getOne(Mockito.any())).thenReturn(userAccount);
        Mockito.when(refreshTokenService.rotateRefreshToken(Mockito.any())).thenReturn(newRefreshToken);
        Mockito.when(jwtService.generateAccessToken(Mockito.any())).thenReturn(newToken);

        AuthResponse expectedAuthResponse = AuthResponse.builder()
                .refreshToken(newRefreshToken)
                .accessToken(newToken)
                .role(userAccount.getRole().getDescription()).build();


        AuthResponse actualAuthResponse = authService.refreshToken(Mockito.any());

        Assertions.assertEquals(expectedAuthResponse.getRefreshToken(), actualAuthResponse.getRefreshToken());
        Assertions.assertEquals(expectedAuthResponse.getAccessToken(), actualAuthResponse.getAccessToken());
        Assertions.assertEquals(expectedAuthResponse.getRole(), actualAuthResponse.getRole());
    }

    @Test
    void logout() {
        UserAccount userAccount = new UserAccount();
        userAccount.setId("aaaaawddd");

        Mockito.doNothing();
    }
}