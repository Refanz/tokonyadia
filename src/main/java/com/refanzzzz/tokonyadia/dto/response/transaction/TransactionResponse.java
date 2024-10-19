package com.refanzzzz.tokonyadia.dto.response.transaction;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionResponse {
    private String id;
    private String transactionDate;
    private String customerId;
}
