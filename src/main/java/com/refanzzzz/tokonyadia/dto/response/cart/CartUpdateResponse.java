package com.refanzzzz.tokonyadia.dto.response.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartUpdateResponse {
    private String cartId;
    private String cartDetailId;
    private Integer qty;
    private String productName;
    private Long price;
}
