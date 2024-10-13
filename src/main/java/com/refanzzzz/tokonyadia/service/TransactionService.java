package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.Transaction;

public interface TransactionService extends Service<TransactionResponse, TransactionRequest, Transaction> {
}
