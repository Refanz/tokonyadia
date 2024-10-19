package com.refanzzzz.tokonyadia.dto.response.cart;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CartResponse {
    private String id;
    private String storeName;
    private String customerName;
    private List<CartDetailResponse> cartDetails;
}
