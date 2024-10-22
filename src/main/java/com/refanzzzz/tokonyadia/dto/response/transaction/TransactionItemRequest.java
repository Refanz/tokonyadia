package com.refanzzzz.tokonyadia.dto.response.transaction;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionItemRequest {
    private String id;
    private Integer qty;
    private Long price;
    private String productId;
    private String transactionId;
}
