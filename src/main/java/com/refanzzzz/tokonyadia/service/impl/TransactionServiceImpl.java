package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.entitiy.Transaction;
import com.refanzzzz.tokonyadia.service.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public List<Transaction> getAllTransaction() {
        return List.of();
    }

    @Override
    public Transaction getTransactionById(String id) {
        return null;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction updateStore(String id, Transaction transaction) {
        return null;
    }

    @Override
    public String deleteTransactionById(String id) {
        return "";
    }
}
