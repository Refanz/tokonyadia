package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionDetailResponse {
    private String id;
    private Long price;
    private Integer qty;
    private String productId;
    private String transactionId;
}
