package com.refanzzzz.tokonyadia.dto.request;

import com.refanzzzz.tokonyadia.entity.UserAccount;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class MerchantRequest extends UserAccount {
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private SearchingPagingAndSortingRequest searchRequest;
}
