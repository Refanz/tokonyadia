package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TransactionRequest extends SearchingPagingAndSortingRequest {
    private LocalDateTime transactionDate;
    private String customerId;
}
