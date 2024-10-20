package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PaymentRequest {
    private String transactionId;
}
