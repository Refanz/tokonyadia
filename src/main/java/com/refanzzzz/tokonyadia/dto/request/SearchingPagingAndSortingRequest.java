package com.refanzzzz.tokonyadia.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class SearchingPagingAndSortingRequest {
    private String query;
    private Integer page;
    private Integer size;
    private String sortBy;

    public Integer getPage() {
        return (page <= 0) ? 0 : page - 1;
    }
}
