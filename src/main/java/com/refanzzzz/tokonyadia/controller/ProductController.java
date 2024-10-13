package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.ProductResponse;
import com.refanzzzz.tokonyadia.dto.response.StoreResponse;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.refanzzzz.tokonyadia.constant.Constant.PRODUCT_API;

@RestController
@RequestMapping(PRODUCT_API)
@AllArgsConstructor
public class ProductController implements Controller<CommonResponse<ProductResponse>, ProductRequest> {

    private ProductService productService;
    private StoreService storeService;

    @GetMapping
    @Override
    public ResponseEntity<CommonResponse<ProductResponse>> getAll(
            @RequestParam(name = "q", required = false) String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", required = false) String sort) {

        ProductRequest productRequest = ProductRequest.builder()
                .query(query)
                .page(page)
                .size(size)
                .sortBy(sort)
                .build();

        Page<ProductResponse> productResponses = productService.getAll(productRequest);
        return ResponseUtil.createResponseWithPaging(HttpStatus.OK, "Successfully get all product", productResponses);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<ProductResponse>> getById(@PathVariable String id) {
        ProductResponse productResponse = productService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully get product with id: " + id, productResponse);
    }

    @PostMapping
    @Override
    public ResponseEntity<CommonResponse<ProductResponse>> create(@RequestBody ProductRequest request) {
        StoreResponse storeResponse = storeService.getById(request.getStoreId());
        request.setStoreId(storeResponse.getId());
        ProductResponse productResponse = productService.create(request);

        return ResponseUtil.createResponse(HttpStatus.CREATED, "Successfully create new product", productResponse);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<ProductResponse>> remove(@PathVariable String id) {
        productService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully delete product with id: ", null);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CommonResponse<ProductResponse>> update(@PathVariable String id, @RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.update(id, request);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully update product with id: " + id, productResponse);
    }
}
