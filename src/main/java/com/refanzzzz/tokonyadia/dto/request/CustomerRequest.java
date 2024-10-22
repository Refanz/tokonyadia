package com.refanzzzz.tokonyadia.dto.request;

import com.refanzzzz.tokonyadia.dto.request.auth.UserAccountRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class CustomerRequest extends UserAccountRequest {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
}
