package com.refanzzzz.tokonyadia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class MerchantRequest extends UserAccountRequest {
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private SearchingPagingAndSortingRequest searchRequest;
}
