package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String role;
}
