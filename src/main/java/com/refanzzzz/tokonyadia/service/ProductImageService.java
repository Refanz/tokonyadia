package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.dto.response.file.FileDownloadResponse;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    ProductImage create(MultipartFile multipartFile, Product product);

    List<ProductImage> createBulk(List<MultipartFile> multipartFiles, Product product);

    ProductImage update(String productImageId, MultipartFile multipartFile);

    void deleteProductImageById(String productImageId);

    FileDownloadResponse downloadImage(String productImageId);
}
