package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.PaymentRequest;
import com.refanzzzz.tokonyadia.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);
}