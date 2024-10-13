package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class CustomerRequest extends SearchingPagingAndSortingRequest {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
}
