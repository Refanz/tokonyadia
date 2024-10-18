package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartDetailRequest {
    private String cartId;
    private String productId;
    private Integer qty;
}
