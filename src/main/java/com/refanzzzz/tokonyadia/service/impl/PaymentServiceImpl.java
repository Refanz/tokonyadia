package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.PaymentRequest;
import com.refanzzzz.tokonyadia.dto.response.PaymentResponse;
import com.refanzzzz.tokonyadia.entity.Transaction;
import com.refanzzzz.tokonyadia.repository.PaymentRepository;
import com.refanzzzz.tokonyadia.service.PaymentService;
import com.refanzzzz.tokonyadia.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionService transactionService;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Transaction transaction = transactionService.getOne(request.getTransactionId());

        transaction.getTransactionDetails().forEach(detail -> {

        });
    }
}
