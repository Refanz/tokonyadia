package com.refanzzzz.tokonyadia.dto.request.auth;

import com.refanzzzz.tokonyadia.dto.request.SearchingPagingAndSortingRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class UserAccountRequest extends SearchingPagingAndSortingRequest {
    private String username;
    private String password;
    private String role;
}
