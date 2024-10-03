package com.refanzzzz.tokonyadia.util;

import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<CommonResponse<T>> createResponse(HttpStatus httpStatus, String message, T data) {
        CommonResponse<T> response = CommonResponse.<T>builder()
                .status(httpStatus.value())
                .message(message)
                .data(data)
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }

    public static <T> ResponseEntity<CommonResponse<T>> createResponseWithPaging(HttpStatus httpStatus, String message, Page<T> page) {
        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(page.getTotalPages())
                .page(page.getPageable().getPageNumber())
                .totalItems(page.getTotalElements())
                .size(page.getSize())
                .build();

        CommonResponse<T> response = CommonResponse.<T>builder()
                .status(httpStatus.value())
                .message(message)
                .paging(pagingResponse)
                .data((T) page.getContent())
                .build();

        return ResponseEntity.status(httpStatus).body(response);
    }
}
