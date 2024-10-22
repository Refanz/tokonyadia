package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionCreateRequest;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionSearchRequest;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse createCustomerTransaction(TransactionCreateRequest request);

    TransactionResponse addTransactionItem(String transactionId, TransactionDetailRequest request);

    Page<TransactionResponse> getAll(TransactionSearchRequest request);

    Transaction getOne(String transactionId);

    TransactionResponse getTransactionById(String transactionId);
}
