package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.entitiy.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransaction();

    Transaction getTransactionById(String id);

    Transaction addTransaction(Transaction transaction);

    Transaction updateStore(String id, Transaction transaction);

    String deleteTransactionById(String id);
}
