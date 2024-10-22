package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.midtrans.MidtransNotificationRequest;
import com.refanzzzz.tokonyadia.dto.request.PaymentRequest;
import com.refanzzzz.tokonyadia.dto.response.PaymentResponse;
import com.refanzzzz.tokonyadia.service.PaymentService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.PAYMENT_API)
@RequiredArgsConstructor
@Tag(name = "Payment", description = "APIs for Payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest request) {
        PaymentResponse paymentResponse = paymentService.createPayment(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "", paymentResponse);
    }

    @PostMapping(path = "/notification")
    public ResponseEntity<?> handlePaymentNotification(@RequestBody MidtransNotificationRequest request) {
        paymentService.handlePaymentNotification(request);
        return ResponseUtil.createResponse(HttpStatus.OK, "Transaction status is updated", null);
    }

}
