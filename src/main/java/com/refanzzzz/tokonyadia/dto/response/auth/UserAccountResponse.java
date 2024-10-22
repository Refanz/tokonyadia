package com.refanzzzz.tokonyadia.dto.response.auth;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserAccountResponse {
    private String id;
    private String username;
    private String role;
}
