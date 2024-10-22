package com.refanzzzz.tokonyadia.dto.response.cart;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartCheckoutResponse {
    private String customerId;
    private String storeId;
    private List<CartDetailResponse> cartDetails;
}
