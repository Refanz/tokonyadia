package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.TransactionDetailRequest;
import com.refanzzzz.tokonyadia.dto.response.TransactionDetailResponse;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.Transaction;
import com.refanzzzz.tokonyadia.entity.TransactionDetail;
import com.refanzzzz.tokonyadia.repository.TransactionDetailRepository;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.service.TransactionDetailService;
import com.refanzzzz.tokonyadia.service.TransactionService;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;
    private final TransactionService transactionService;
    private final ProductService productService;

    @Override
    public Page<TransactionDetailResponse> getAll(TransactionDetailRequest request) {

        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);
        Page<TransactionDetail> transactionDetails = transactionDetailRepository.findAll(pageable);

        return transactionDetails.map(this::toTransactionDetailResponse);
    }

    @Override
    public TransactionDetailResponse getById(String id) {
        TransactionDetail transactionDetail = getOne(id);
        return toTransactionDetailResponse(transactionDetail);
    }

    @Override
    public TransactionDetailResponse create(TransactionDetailRequest data) {

        Transaction transaction = transactionService.getOne(data.getTransactionId());
        Product product = productService.getOne(data.getProductId());

        TransactionDetail transactionDetail = TransactionDetail
                .builder()
                .transaction(transaction)
                .product(product)
                .qty(data.getQty())
                .price(data.getPrice())
                .build();
        transactionDetailRepository.saveAndFlush(transactionDetail);

        return toTransactionDetailResponse(transactionDetail);
    }

    @Override
    public TransactionDetail getOne(String id) {
        return transactionDetailRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction detail is not found!"));
    }

    @Override
    public void remove(String id) {
        TransactionDetail transactionDetail = getOne(id);
        transactionDetailRepository.delete(transactionDetail);
    }

    @Override
    public TransactionDetailResponse update(String id, TransactionDetailRequest data) {
        TransactionDetail transactionDetail = getOne(id);
        transactionDetail.setPrice(data.getPrice());
        transactionDetail.setQty(data.getQty());

        transactionDetailRepository.saveAndFlush(transactionDetail);
        return toTransactionDetailResponse(transactionDetail);
    }

    private TransactionDetailResponse toTransactionDetailResponse(TransactionDetail transactionDetail) {
        return TransactionDetailResponse
                .builder()
                .id(transactionDetail.getId())
                .price(transactionDetail.getPrice())
                .qty(transactionDetail.getQty())
                .transactionId(transactionDetail.getTransaction().getId())
                .productId(transactionDetail.getProduct().getId())
                .build();
    }
}
