package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.constant.Constant;
import com.refanzzzz.tokonyadia.dto.request.MerchantRequest;
import com.refanzzzz.tokonyadia.dto.request.SearchingPagingAndSortingRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.MerchantResponse;
import com.refanzzzz.tokonyadia.service.MerchantService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = Constant.MERCHANT_API)
@RequiredArgsConstructor
public class MerchantController implements Controller<CommonResponse<MerchantResponse>, MerchantRequest> {

    private final MerchantService merchantService;

    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<MerchantResponse>> getAll(
            @RequestParam(required = false, name = "q") String query,
            @RequestParam(required = false, name = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, name = "size", defaultValue = "10") Integer size,
            @RequestParam(required = false, name = "sortBy") String sort) {

        SearchingPagingAndSortingRequest searchRequest = SearchingPagingAndSortingRequest.builder()
                .size(size)
                .page(page)
                .query(query)
                .sortBy(sort)
                .build();

        MerchantRequest merchantRequest = MerchantRequest.builder()
                .searchRequest(searchRequest)
                .build();

        Page<MerchantResponse> merchantResponsePage = merchantService.getAll(merchantRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "", merchantResponsePage);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<MerchantResponse>> getById(@PathVariable String id) {
        MerchantResponse merchantResponse = merchantService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "", merchantResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<CommonResponse<MerchantResponse>> create(@RequestBody MerchantRequest request) {
        MerchantResponse merchantResponse = merchantService.create(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "", merchantResponse);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<MerchantResponse>> remove(@PathVariable String id) {
        merchantService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "", null);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<MerchantResponse>> update(@PathVariable String id, @RequestBody MerchantRequest request) {
        MerchantResponse merchantResponse = merchantService.update(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "", merchantResponse);
    }
}
