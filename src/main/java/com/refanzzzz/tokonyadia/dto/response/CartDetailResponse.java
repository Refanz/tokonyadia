package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartDetailResponse {
    private String id;
    private String cartId;
    private String productId;
    private Integer qty;
}
