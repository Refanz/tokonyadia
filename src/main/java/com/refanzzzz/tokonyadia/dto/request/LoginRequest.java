package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginRequest {
    private String username;
    private String password;
}
