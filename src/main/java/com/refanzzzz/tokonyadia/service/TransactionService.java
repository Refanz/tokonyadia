package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);

    TransactionResponse addTransactionItem(String transactionId, TransactionDetailRequest request);

    Page<TransactionResponse> getAll(TransactionRequest request);

    Transaction getOne(String transactionId);

    TransactionResponse getTransactionById(String transactionId);

//    TransactionResponse checkoutTransaction(String transactionId);
}
