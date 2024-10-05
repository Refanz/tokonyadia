package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class TransactionDetailRequest extends SearchingPagingAndSortingRequest {
    private Long price;
    private Integer qty;
    private String productId;
    private String transactionId;
}
