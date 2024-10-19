package com.refanzzzz.tokonyadia.dto.request.cart;

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
