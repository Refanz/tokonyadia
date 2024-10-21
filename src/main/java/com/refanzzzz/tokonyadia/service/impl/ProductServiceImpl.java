package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.dto.response.ProductResponse;
import com.refanzzzz.tokonyadia.dto.response.StoreResponse;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.ProductImage;
import com.refanzzzz.tokonyadia.entity.Store;
import com.refanzzzz.tokonyadia.repository.ProductRepository;
import com.refanzzzz.tokonyadia.service.ProductImageService;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.specification.ProductSpecification;
import com.refanzzzz.tokonyadia.util.MapperUtil;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductImageService productImageService;
    private StoreService storeService;

    @Transactional(readOnly = true)
    @Override
    public Page<ProductResponse> getAll(ProductRequest request) {

        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);
        Specification<Product> specification = ProductSpecification.getProductSpecification(request);

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        return productPage.map(MapperUtil::toProductResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getById(String id) {
        Product product = getOne(id);
        return MapperUtil.toProductResponse(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse create(ProductRequest request, List<MultipartFile> multipartFiles) {

        log.info(multipartFiles.get(0).getResource().getFilename());
        StoreResponse storeResponse = storeService.getById(request.getStoreId());

        Store store = Store.builder()
                .id(storeResponse.getId())
                .name(storeResponse.getName())
                .noSiup(storeResponse.getNoSiup())
                .phoneNumber(storeResponse.getPhoneNumber())
                .address(storeResponse.getAddress())
                .build();

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .stock(request.getStock())
                .price(request.getPrice())
                .store(store)
                .build();

        productRepository.saveAndFlush(product);

        if (!multipartFiles.isEmpty()) {
            List<ProductImage> images = productImageService.createBulk(multipartFiles, product);
            product.setProductImages(images);
        }

        return MapperUtil.toProductResponse(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(String id) {
        Product product = getOne(id);
        productRepository.delete(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse update(String id, ProductRequest request, List<MultipartFile> multipartFiles) {
        Product product = getOne(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());

        if (!multipartFiles.isEmpty()) {
            List<ProductImage> images = productImageService.createBulk(multipartFiles, product);

            if (product.getProductImages().isEmpty()) {
                product.setProductImages(images);
            } else {
                product.getProductImages().addAll(images);
            }
        }

        productRepository.saveAndFlush(product);

        return MapperUtil.toProductResponse(product);
    }

    @Override
    public ProductResponse updateProductImage(String imageId, MultipartFile multipartFile) {
        ProductImage productImage = productImageService.update(imageId, multipartFile);
        return MapperUtil.toProductResponse(productImage.getProduct());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProductImage(String imageId) {
        productImageService.deleteProductImageById(imageId);
    }

    @Transactional(readOnly = true)
    @Override
    public Product getOne(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found!"));
    }
}
