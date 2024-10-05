package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.TransactionResponse;
import com.refanzzzz.tokonyadia.entitiy.Transaction;
import com.refanzzzz.tokonyadia.repository.TransactionRepository;
import com.refanzzzz.tokonyadia.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Override
    public Page<TransactionResponse> getAll(TransactionRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getPage());
        Page<Transaction> transactionPage = transactionRepository.findAll(pageable);

        return transactionPage.map(new Function<Transaction, TransactionResponse>() {
            @Override
            public TransactionResponse apply(Transaction transaction) {
                return toTransactionResponse(transaction);
            }
        });
    }

    @Override
    public TransactionResponse getById(String id) {
        Transaction transaction = getTransaction(id);
        if (transaction == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found!");

        return toTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse insert(TransactionRequest data) {
        Transaction transaction = Transaction.builder()
                .transactionDate(data.getTransactionDate())
                .build();

        transactionRepository.saveAndFlush(transaction);
        return toTransactionResponse(transaction);
    }

    @Override
    public void remove(String id) {
        Transaction transaction = getTransaction(id);
        if (transaction == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found");

        transactionRepository.delete(transaction);
    }

    @Override
    public TransactionResponse update(String id, TransactionRequest data) {
        Transaction transaction = getTransaction(id);

        if (transaction == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found");

        transaction.setTransactionDate(data.getTransactionDate());
        transactionRepository.saveAndFlush(transaction);

        return toTransactionResponse(transaction);
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionDate(transaction.getTransactionDate())
                .customer(transaction.getCustomer())
                .build();
    }

    private Transaction getTransaction(String id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return optionalTransaction.orElse(null);
    }
}
