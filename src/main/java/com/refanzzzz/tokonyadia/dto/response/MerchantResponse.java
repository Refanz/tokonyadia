package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MerchantResponse {
    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String userId;
}
