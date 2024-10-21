package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.constant.FileType;
import com.refanzzzz.tokonyadia.dto.response.file.FileDownloadResponse;
import com.refanzzzz.tokonyadia.dto.response.file.FileInfo;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.ProductImage;
import com.refanzzzz.tokonyadia.repository.ProductImageRepository;
import com.refanzzzz.tokonyadia.service.FileStorageService;
import com.refanzzzz.tokonyadia.service.ProductImageService;
import com.refanzzzz.tokonyadia.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final FileStorageService fileStorageService;
    private final ImageUtil imageUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductImage create(MultipartFile multipartFile, Product product) {
        FileInfo fileInfo = fileStorageService.storeFile(FileType.IMAGE, "menu", multipartFile, imageUtil.getImageContentTypes());
        ProductImage productImage = ProductImage.builder()
                .path(fileInfo.getPath())
                .contentType(multipartFile.getContentType())
                .filename(fileInfo.getFilename())
                .size(multipartFile.getSize())
                .product(product)
                .build();

        return productImageRepository.saveAndFlush(productImage);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ProductImage> createBulk(List<MultipartFile> multipartFiles, Product product) {
        return multipartFiles.stream().map(multipartFile -> create(multipartFile, product)).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductImage update(String productImageId, MultipartFile multipartFile) {
        ProductImage productImage = findProductImageById(productImageId);
        FileInfo fileInfo = fileStorageService.storeFile(FileType.IMAGE, "menu", multipartFile, imageUtil.getImageContentTypes());

        fileStorageService.deleteFile(productImage.getPath());
        productImage.setPath(fileInfo.getPath());
        productImageRepository.saveAndFlush(productImage);

        return productImage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProductImageById(String productImageId) {
        ProductImage productImage = findProductImageById(productImageId);
        fileStorageService.deleteFile(productImage.getPath());
        productImageRepository.delete(productImage);
    }

    @Transactional(readOnly = true)
    @Override
    public FileDownloadResponse downloadImage(String productImageId) {
        ProductImage productImage = findProductImageById(productImageId);
        Resource resource = fileStorageService.readFile(productImage.getPath());

        return FileDownloadResponse.builder()
                .resource(resource)
                .contentType(productImage.getContentType())
                .build();
    }

    @Transactional(readOnly = true)
    public ProductImage findProductImageById(String id) {
        return productImageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found!"));
    }
}
