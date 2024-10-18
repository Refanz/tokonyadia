package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartRequest {
    private String customerId;
    private String storeId;
}
