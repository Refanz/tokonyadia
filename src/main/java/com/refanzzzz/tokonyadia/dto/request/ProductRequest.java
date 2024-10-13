package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductRequest extends SearchingPagingAndSortingRequest {
    private String name;
    private String description;
    private Long price;
    private Integer stock;
    private String storeId;
}
