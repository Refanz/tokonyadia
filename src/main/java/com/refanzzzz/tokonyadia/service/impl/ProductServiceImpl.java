package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.dto.response.ProductResponse;
import com.refanzzzz.tokonyadia.dto.response.StoreResponse;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.Store;
import com.refanzzzz.tokonyadia.repository.ProductRepository;
import com.refanzzzz.tokonyadia.service.ProductService;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.specification.ProductSpecification;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private StoreService storeService;

    @Override
    public Page<ProductResponse> getAll(ProductRequest request) {

        Sort sortBy = SortUtil.parseSort(request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);
        Specification<Product> specification = ProductSpecification.getProductSpecification(request);

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        return productPage.map(this::toProductResponse);
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = getOne(id);
        return toProductResponse(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProductResponse create(ProductRequest request) {
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

        return toProductResponse(product);
    }

    @Override
    public void remove(String id) {
        Product product = getOne(id);
        productRepository.delete(product);
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        Product product = getOne(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setStock(request.getStock());
        product.setPrice(request.getPrice());

        productRepository.saveAndFlush(product);

        return toProductResponse(product);
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .storeId(product.getStore().getId())
                .build();
    }

    @Override
    public Product getOne(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not found!"));
    }
}
