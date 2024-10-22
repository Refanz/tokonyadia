package com.refanzzzz.tokonyadia.dto.request.transaction;

import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionItemRequest;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionCreateRequest {
    private String customerId;
    private List<TransactionItemRequest> transactionItemRequests;
}
