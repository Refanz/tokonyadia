package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.dto.response.ProductResponse;
import com.refanzzzz.tokonyadia.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAll(ProductRequest request);

    ProductResponse getById(String id);

    ProductResponse create(ProductRequest request, List<MultipartFile> multipartFiles);

    Product getOne(String id);

    void remove(String id);

    ProductResponse update(String id, ProductRequest request, List<MultipartFile> multipartFiles);

    ProductResponse updateProductImage(String imageId, MultipartFile multipartFile);

    void deleteProductImage(String imageId);
}
