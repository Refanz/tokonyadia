package com.refanzzzz.tokonyadia.dto.request.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartDetailRequest {
    private String productId;
    private Integer qty;
}
