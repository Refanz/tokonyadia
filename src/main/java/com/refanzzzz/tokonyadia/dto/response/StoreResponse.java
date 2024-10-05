package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class StoreResponse {
    private String id;
    private String noSiup;
    private String name;
    private String address;
    private String phoneNumber;
    private PagingResponse paging;
}
