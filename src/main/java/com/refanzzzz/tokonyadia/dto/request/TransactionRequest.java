package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequest {
    private LocalDateTime transactionDate;
    private String customerId;
}
