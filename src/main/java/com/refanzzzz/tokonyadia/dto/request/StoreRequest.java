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
public class StoreRequest extends SearchingPagingAndSortingRequest {
    private String name;
    private String noSiup;
    private String address;
    private String phoneNumber;
}
