package com.refanzzzz.tokonyadia.dto.request.cart;

import com.refanzzzz.tokonyadia.dto.request.SearchingPagingAndSortingRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class CartSearchRequest extends SearchingPagingAndSortingRequest {
    private String customerId;
}
