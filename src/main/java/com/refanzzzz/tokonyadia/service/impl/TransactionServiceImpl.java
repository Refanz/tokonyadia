package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.TransactionStatus;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionCreateRequest;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionSearchRequest;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionItemRequest;
import com.refanzzzz.tokonyadia.dto.response.transaction.TransactionResponse;
import com.refanzzzz.tokonyadia.entity.Customer;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.Transaction;
import com.refanzzzz.tokonyadia.entity.TransactionDetail;
import com.refanzzzz.tokonyadia.repository.TransactionDetailRepository;
import com.refanzzzz.tokonyadia.repository.TransactionRepository;
import com.refanzzzz.tokonyadia.service.CustomerService;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.service.TransactionService;
import com.refanzzzz.tokonyadia.specification.TransactionSpecification;
import com.refanzzzz.tokonyadia.util.MapperUtil;
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
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private TransactionDetailRepository transactionDetailRepository;
    private CustomerService customerService;
    private ProductService productService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse createCustomerTransaction(TransactionCreateRequest transactionCreateRequest) {
        Customer customer = customerService.getOne(transactionCreateRequest.getCustomerId());

        Transaction transaction = Transaction.builder()
                .transactionStatus(TransactionStatus.PENDING)
                .customer(customer)
                .transactionDetails(new ArrayList<>())
                .build();

        transactionRepository.saveAndFlush(transaction);

        List<TransactionDetail> transactionDetails = new ArrayList<>();

        for (TransactionItemRequest itemRequest : transactionCreateRequest.getTransactionItemRequests()) {
            Product product = productService.getOne(itemRequest.getProductId());

            transactionDetails.add(TransactionDetail.builder()
                    .transaction(transaction)
                    .product(product)
                    .qty(itemRequest.getQty())
                    .price(itemRequest.getPrice())
                    .build());
        }

        transactionDetailRepository.saveAllAndFlush(transactionDetails);

        return MapperUtil.toTransactionResponse(transaction);
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

        return MapperUtil.toTransactionResponse(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionResponse> getAll(TransactionSearchRequest request) {
        Specification<Transaction> specification = TransactionSpecification.getTransactionSpecification(request);
        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);

        Page<Transaction> transactionPage = transactionRepository.findAll(specification, pageable);

        return transactionPage.map(MapperUtil::toTransactionResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionResponse getTransactionById(String id) {
        Transaction transaction = getOne(id);
        return MapperUtil.toTransactionResponse(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Transaction getOne(String transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        return optionalTransaction.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction is not found!"));
    }
}
