package com.refanzzzz.tokonyadia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.dto.response.CommonResponse;
import com.refanzzzz.tokonyadia.dto.response.ProductResponse;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.refanzzzz.tokonyadia.constant.Constant.PRODUCT_API;

@RestController
@RequestMapping(path = PRODUCT_API)
@AllArgsConstructor
public class ProductController {

    private ProductService productService;
    private ObjectMapper objectMapper;

    @GetMapping
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
    public ResponseEntity<CommonResponse<ProductResponse>> getById(@PathVariable String id) {
        ProductResponse productResponse = productService.getById(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully get product with id: " + id, productResponse);
    }

    @PreAuthorize("hasRole('ROLE_MERCHANT')")
    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> create(
            @RequestParam(name = "images", required = false) List<MultipartFile> multipartFiles,
            @RequestParam(name = "product") String product) {
        try {
            ProductRequest productRequest = objectMapper.readValue(product, ProductRequest.class);
            ProductResponse productResponse = productService.create(productRequest, multipartFiles);

            return ResponseUtil.createResponse(HttpStatus.CREATED, "Successfully create new product", productResponse);
        } catch (Exception e) {
            return ResponseUtil.createResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> remove(@PathVariable String id) {
        productService.remove(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "Successfully delete product with id: ", null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> update(
            @PathVariable String id,
            @RequestParam(name = "product") String product,
            @RequestParam(name = "images") List<MultipartFile> multipartFiles) {
        try {
            ProductRequest productRequest = objectMapper.readValue(product, ProductRequest.class);
            ProductResponse productResponse = productService.update(id, productRequest, multipartFiles);

            return ResponseUtil.createResponse(HttpStatus.OK, "Successfully update product with id: " + id, productResponse);
        } catch (Exception e) {
            return ResponseUtil.createResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PatchMapping("/images/{id}")
    public ResponseEntity<?> updateSpecifiedProductImage(
            @PathVariable String id,
            @RequestParam(name = "image") MultipartFile multipartFile
    ) {
        ProductResponse productResponse = productService.updateProductImage(id, multipartFile);
        return ResponseUtil.createResponse(HttpStatus.OK, "", productResponse);
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteSpecifiedProductImage(@PathVariable String id) {
        productService.deleteProductImage(id);
        return ResponseUtil.createResponse(HttpStatus.OK, "", null);
    }
}
