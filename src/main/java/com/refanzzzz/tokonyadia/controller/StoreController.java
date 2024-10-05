package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.dto.request.CustomerRequest;
import com.refanzzzz.tokonyadia.dto.request.StoreRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.CustomerResponse;
import com.refanzzzz.tokonyadia.dto.response.StoreResponse;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
@AllArgsConstructor
public class StoreController implements Controller<CommonResponse<StoreResponse>, StoreRequest> {

    private StoreService storeService;

    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> getAll(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false) String sort) {

        StoreRequest storeRequest = StoreRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .sortBy(sort)
                .build();

        Page<StoreResponse> storeResponsePage = storeService.getAll(storeRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "Successfully get all store", storeResponsePage);
    }

    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> getById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> insert(StoreRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> remove(String id) {
        return null;
    }

    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> update(String id, StoreRequest request) {
        return null;
    }
}
