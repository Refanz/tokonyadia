package com.refanzzzz.tokonyadia.dto.request.midtrans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MidtransNotificationRequest {

    @JsonProperty(value = "transaction_id")
    private String transactionId;

    @JsonProperty(value = "status_message")
    private String statusMessage;

    @JsonProperty(value = "fraud_status")
    private String fraudStatus;

    @JsonProperty(value = "transaction_status")
    private String transactionStatus;

    @JsonProperty(value = "status_code")
    private String statusCode;

    @JsonProperty(value = "reference_id")
    private String referenceId;

    @JsonProperty(value = "signature_key")
    private String signatureKey;

    @JsonProperty(value = "merchant_id")
    private String merchantId;

    @JsonProperty(value = "gross_amount")
    private String grossAmount;

    @JsonProperty(value = "transaction_type")
    private String transactionType;

    @JsonProperty(value = "acquirer")
    private String acquirer;

    @JsonProperty(value = "settlement_time")
    private String settlementTime;

    @JsonProperty(value = "issuer")
    private String issuer;

    @JsonProperty(value = "payment_type")
    private String paymentType;

    @JsonProperty(value = "transaction_time")
    private String transactionTime;

    @JsonProperty(value = "currency")
    private String currency;

    @JsonProperty(value = "expiry_time")
    private String expiryTime;

    @JsonProperty(value = "order_id")
    private String orderId;
}