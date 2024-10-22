package com.refanzzzz.tokonyadia.dto.request.midtrans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MidtransPaymentRequest {
    @JsonProperty(value = "transaction_details")
    private MidtransTransactionRequest transactionDetails;

    @JsonProperty(value = "enabled_payments")
    private List<String> enabledPayments;
}
