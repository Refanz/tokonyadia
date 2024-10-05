package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.TransactionResponse;
import com.refanzzzz.tokonyadia.service.TransactionService;
import org.springframework.data.domain.Page;

public class TransactionDetailServiceImpl implements TransactionService {

    @Override
    public Page<TransactionResponse> getAll(TransactionRequest request) {
        return null;
    }

    @Override
    public TransactionResponse getById(String id) {
        return null;
    }

    @Override
    public TransactionResponse insert(TransactionRequest data) {
        return null;
    }

    @Override
    public void remove(String id) {

    }

    @Override
    public TransactionResponse update(String id, TransactionRequest data) {
        return null;
    }
}
