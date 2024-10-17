package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.Customer;
import com.refanzzzz.tokonyadia.entity.Transaction;
import com.refanzzzz.tokonyadia.repository.TransactionRepository;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.service.TransactionService;
import com.refanzzzz.tokonyadia.specification.TransactionSpecification;
import com.refanzzzz.tokonyadia.util.DateUtil;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private CustomerService customerService;

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Customer customer = customerService.getOne(transactionRequest.getCustomerId());

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDetails(new ArrayList<>())
                .build();

        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);

        return toTransactionResponse(savedTransaction);
    }

    @Override
    public Page<TransactionResponse> getAll(TransactionRequest request) {

        Specification<Transaction> specification = TransactionSpecification.getTransactionSpecification(request);
        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);

        Page<Transaction> transactionPage = transactionRepository.findAll(specification, pageable);

        return transactionPage.map(this::toTransactionResponse);
    }

    @Override
    public TransactionResponse getById(String id) {
        Transaction transaction = getOne(id);
        return toTransactionResponse(transaction);
    }

    @Override
    public TransactionResponse create(TransactionRequest request) {
        Customer customer = customerService.getOne(request.getCustomerId());
        Transaction transaction = Transaction.builder()
                .transactionDate(LocalDateTime.parse(request.getTransactionDate()))
                .customer(customer)
                .build();

        transactionRepository.saveAndFlush(transaction);
        return toTransactionResponse(transaction);
    }

    @Override
    public void remove(String id) {
        Transaction transaction = getOne(id);
        transactionRepository.delete(transaction);
    }

    @Override
    public TransactionResponse update(String id, TransactionRequest request) {
        Transaction transaction = getOne(id);

        //transaction.setTransactionDate(request.getTransactionDate());
        transactionRepository.saveAndFlush(transaction);

        return toTransactionResponse(transaction);
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transactionDate(DateUtil.parseDateToString(transaction.getTransactionDate()))
                .build();
    }

    @Override
    public Transaction getOne(String id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        return optionalTransaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found!"));
    }
}
