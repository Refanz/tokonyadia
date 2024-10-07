package com.refanzzzz.tokonyadia.dto.response;

import com.refanzzzz.tokonyadia.entity.Customer;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionResponse {
    private String id;
    private LocalDateTime transactionDate;
    private Customer customer;
}
