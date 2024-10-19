package com.refanzzzz.tokonyadia.dto.request.cart;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartUpdateRequest {
    private String cartDetailId;
    private Long price;
    private Integer qty;
}
