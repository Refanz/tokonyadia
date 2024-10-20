package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PaymentResponse {
    private String transactionId;
    private Long amount;
    private String paymentStatus;
    private String tokenSnap;
    private String redirectUrl;
}
