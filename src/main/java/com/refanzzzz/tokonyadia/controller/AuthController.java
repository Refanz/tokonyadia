package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.LoginRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.LoginResponse;
import com.refanzzzz.tokonyadia.service.AuthService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Constant.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseUtil.createResponse(HttpStatus.OK, "Login successfully", loginResponse);
    }
}
