package com.refanzzzz.tokonyadia.dto.request.transaction;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionCheckoutRequest {
    private String storeId;
    private String transactionId;
    private String customerId;
}
