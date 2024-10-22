package com.refanzzzz.tokonyadia.dto.request.transaction;

import com.refanzzzz.tokonyadia.dto.request.SearchingPagingAndSortingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class TransactionSearchRequest extends SearchingPagingAndSortingRequest {
    private LocalDateTime minTransactionDate;
    private LocalDateTime maxTransactionDate;
}
