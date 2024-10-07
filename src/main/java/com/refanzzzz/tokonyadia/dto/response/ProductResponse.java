package com.refanzzzz.tokonyadia.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private Integer stock;
    private String description;
    private Long price;
    private String storeId;
}
