package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.dto.request.StoreRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.StoreResponse;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.refanzzzz.tokonyadia.constant.Constant.STORE_API;

@RestController
@RequestMapping(STORE_API)
@AllArgsConstructor
public class StoreController implements Controller<CommonResponse<StoreResponse>, StoreRequest> {

    private StoreService storeService;

    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> getAll(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sort) {

        StoreRequest storeRequest = StoreRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .sortBy(sort)
                .build();

        Page<StoreResponse> storeResponsePage = storeService.getAll(storeRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "Successfully get all store", storeResponsePage);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> getById(@PathVariable String id) {
        StoreResponse storeResponse = storeService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully get store by id", storeResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> create(@RequestBody StoreRequest request) {
        StoreResponse storeResponse = storeService.create(request);
        return ResponseUtil.createResponse(HttpStatus.CREATED, "Successfully create store", storeResponse);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> remove(@PathVariable String id) {
        storeService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully delete store with id: " + id, null);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<StoreResponse>> update(@PathVariable String id, @RequestBody StoreRequest request) {
        StoreResponse storeResponse = storeService.update(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully update store", storeResponse);
    }
}
