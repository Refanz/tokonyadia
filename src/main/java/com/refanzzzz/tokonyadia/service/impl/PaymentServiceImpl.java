package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.client.MidtransClient;
import com.refanzzzz.tokonyadia.constant.PaymentStatus;
import com.refanzzzz.tokonyadia.constant.TransactionStatus;
import com.refanzzzz.tokonyadia.dto.request.midtrans.MidtransNotificationRequest;
import com.refanzzzz.tokonyadia.dto.request.midtrans.MidtransPaymentRequest;
import com.refanzzzz.tokonyadia.dto.request.midtrans.MidtransTransactionRequest;
import com.refanzzzz.tokonyadia.dto.request.PaymentRequest;
import com.refanzzzz.tokonyadia.dto.response.MidtransSnapResponse;
import com.refanzzzz.tokonyadia.dto.response.PaymentResponse;
import com.refanzzzz.tokonyadia.entity.Payment;
import com.refanzzzz.tokonyadia.entity.Transaction;
import com.refanzzzz.tokonyadia.entity.TransactionDetail;
import com.refanzzzz.tokonyadia.repository.PaymentRepository;
import com.refanzzzz.tokonyadia.service.PaymentService;
import com.refanzzzz.tokonyadia.service.TransactionService;
import com.refanzzzz.tokonyadia.util.HashUtil;
import com.refanzzzz.tokonyadia.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionService transactionService;

    private final MidtransClient midtransClient;

    @Value("${midtrans.api.server-key}")
    private String MIDTRANS_API_SERVER_KEY;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Transaction transaction = transactionService.getOne(request.getTransactionId());

        if (transaction.getTransactionDetails().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Checkout first!");

        long amount = 0;

        for (TransactionDetail transactionDetail : transaction.getTransactionDetails()) {
            Integer detailQty = transactionDetail.getQty();
            Long detailPrice = transactionDetail.getPrice();

            amount += detailQty * detailPrice;
        }

        MidtransPaymentRequest midtransPaymentRequest = MidtransPaymentRequest.builder()
                .transactionDetails(MidtransTransactionRequest.builder()
                        .orderId(request.getTransactionId())
                        .grossAmount(amount)
                        .build())
                .enabledPayments(List.of("shopeepay", "gopay", "other_qris", "bca_va"))
                .build();

        String headerValue = "Basic " + Base64.getEncoder().encodeToString(MIDTRANS_API_SERVER_KEY.getBytes(StandardCharsets.UTF_8));
        MidtransSnapResponse midtransSnapResponse = midtransClient.createSnapTransaction(midtransPaymentRequest, headerValue);

        Payment payment = Payment.builder()
                .transaction(transaction)
                .paymentStatus(PaymentStatus.PENDING)
                .amount(amount)
                .tokenSnap(midtransSnapResponse.getToken())
                .redirectUrl(midtransSnapResponse.getRedirectUrl())
                .build();

        paymentRepository.saveAndFlush(payment);

        return MapperUtil.toPaymentResponse(payment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void handlePaymentNotification(MidtransNotificationRequest request) {
        if (!validateSignatureKey(request))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid signature key!");

        Payment payment = paymentRepository.findByTransactionId(request.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found!"));

        PaymentStatus paymentStatus = PaymentStatus.getPaymentStatusByDesc(request.getTransactionStatus());

        if (paymentStatus != null && paymentStatus.equals(PaymentStatus.SETTLEMENT)) {
            payment.setPaymentStatus(paymentStatus);
            payment.getTransaction().setTransactionStatus(TransactionStatus.CONFIRMED);
        }

        paymentRepository.saveAndFlush(payment);
    }

    private boolean validateSignatureKey(MidtransNotificationRequest request) {
        String rawSignature = request.getOrderId() + request.getStatusCode() + request.getGrossAmount() + MIDTRANS_API_SERVER_KEY;
        String signatureKey = HashUtil.encryptThisString(rawSignature);

        return request.getSignatureKey().equals(signatureKey);
    }
}
