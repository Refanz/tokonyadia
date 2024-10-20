package com.refanzzzz.tokonyadia.dto.request.Midtrans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MidtransTransactionRequest {
    @JsonProperty(value = "order_id")
    private String orderId;

    @JsonProperty(value = "gross_amount")
    private Long grossAmount;
}
