package com.refanzzzz.tokonyadia.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(authException.getMessage())
                .build();

        String responseString = objectMapper.writeValueAsString(commonResponse);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(responseString);
    }
}
