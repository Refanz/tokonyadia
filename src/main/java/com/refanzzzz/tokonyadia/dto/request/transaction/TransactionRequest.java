package com.refanzzzz.tokonyadia.dto.request.transaction;

import com.refanzzzz.tokonyadia.dto.request.SearchingPagingAndSortingRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TransactionRequest extends SearchingPagingAndSortingRequest {
    private String transactionDate;
    private String customerId;
    private LocalDateTime minTransactionDate;
    private LocalDateTime maxTransactionDate;
}
