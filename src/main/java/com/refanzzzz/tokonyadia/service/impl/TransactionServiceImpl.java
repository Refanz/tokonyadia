package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.TransactionStatus;
import com.refanzzzz.tokonyadia.dto.request.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.TransactionRequest;
import com.refanzzzz.tokonyadia.dto.response.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.Customer;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.Transaction;
import com.refanzzzz.tokonyadia.entity.TransactionDetail;
import com.refanzzzz.tokonyadia.repository.TransactionRepository;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.service.ProductService;
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

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private CustomerService customerService;
    private ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Customer customer = customerService.getOne(transactionRequest.getCustomerId());

        Transaction transaction = Transaction.builder()
                .transactionStatus(TransactionStatus.PENDING)
                .customer(customer)
                .transactionDetails(new ArrayList<>())
                .build();

        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);

        return toTransactionResponse(savedTransaction);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse addTransactionItem(String transactionId, TransactionDetailRequest request) {
        Transaction transaction = getOne(transactionId);

        Product product = productService.getOne(request.getProductId());

        TransactionDetail transactionDetail = TransactionDetail.builder()
                .product(product)
                .price(product.getPrice())
                .qty(request.getQty())
                .transaction(transaction)
                .build();

        transaction.getTransactionDetails().add(transactionDetail);

        transactionRepository.saveAndFlush(transaction);

        return toTransactionResponse(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionResponse> getAll(TransactionRequest request) {
        Specification<Transaction> specification = TransactionSpecification.getTransactionSpecification(request);
        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);

        Page<Transaction> transactionPage = transactionRepository.findAll(specification, pageable);

        return transactionPage.map(this::toTransactionResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = getOne(id);
        return toTransactionResponse(transaction);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse checkoutTransaction(String transactionId) {
        Transaction transaction = getOne(transactionId);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Transaction getOne(String transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found!"));
    }

    private TransactionResponse toTransactionResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transactionDate(DateUtil.parseDateToString(transaction.getTransactionDate()))
                .build();
    }
}
