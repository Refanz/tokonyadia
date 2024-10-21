package com.refanzzzz.tokonyadia.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .message(accessDeniedException.getMessage())
                .build();

        String responseString = objectMapper.writeValueAsString(commonResponse);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(responseString);
    }
}
