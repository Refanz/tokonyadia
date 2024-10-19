package com.refanzzzz.tokonyadia.dto.response.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartDetailResponse {
    private String id;
    private String productName;
    private Integer qty;
    private Long price;
}
