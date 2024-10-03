package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionDetailRequest {
    private Long price;
    private Integer qty;
    private String productId;
    private String transactionId;
}
