package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CustomerResponse {
    private String id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
}
