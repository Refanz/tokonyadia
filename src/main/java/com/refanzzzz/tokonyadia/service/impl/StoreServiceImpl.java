package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.StoreRequest;
import com.refanzzzz.tokonyadia.dto.response.ProductResponse;
import com.refanzzzz.tokonyadia.dto.response.StoreResponse;
import com.refanzzzz.tokonyadia.entity.Product;
import com.refanzzzz.tokonyadia.entity.Store;
import com.refanzzzz.tokonyadia.repository.StoreRepository;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.specification.StoreSpecification;
import com.refanzzzz.tokonyadia.util.SortUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepository;

    @Override
    public Page<StoreResponse> getAll(StoreRequest request) {
        Specification<Store> specification = StoreSpecification.getStoreSpecification(request);

        Sort sortBy = SortUtil.parseSort(request.getSortBy());

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sortBy);

        Page<Store> storePage = storeRepository.findAll(specification, pageable);

        return storePage.map(new Function<Store, StoreResponse>() {
            @Override
            public StoreResponse apply(Store store) {
                return toStoreProductResponse(store);
            }
        });
    }

    @Override
    public StoreResponse getById(String id) {
        Store store = getStore(id);
        return toStoreResponse(store);
    }

    @Override
    public StoreResponse insert(StoreRequest data) {
        Store store = Store.builder()
                .name(data.getName())
                .noSiup(data.getNoSiup())
                .address(data.getAddress())
                .phoneNumber(data.getPhoneNumber())
                .build();

        storeRepository.saveAndFlush(store);

        return toStoreResponse(store);
    }

    @Override
    public void remove(String id) {
        Store store = getStore(id);
        storeRepository.delete(store);
    }

    @Override
    public StoreResponse update(String id, StoreRequest data) {
        Store store = getStore(id);

        store.setName(data.getName());
        store.setNoSiup(data.getNoSiup());
        store.setPhoneNumber(data.getPhoneNumber());
        store.setAddress(data.getAddress());

        storeRepository.saveAndFlush(store);
        return toStoreResponse(store);
    }

    private Store getStore(String id) {
        Optional<Store> storeOptional = storeRepository.findById(id);
        return storeOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store is not found!"));
    }

    private StoreResponse toStoreResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .noSiup(store.getNoSiup())
                .phoneNumber(store.getPhoneNumber())
                .build();
    }

    private StoreResponse toStoreProductResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .products(store.getProductList().stream().map(new Function<Product, ProductResponse>() {
                    @Override
                    public ProductResponse apply(Product product) {
                        return ProductResponse.builder()
                                .name(product.getName())
                                .build();
                    }
                }).toList())
                .noSiup(store.getNoSiup())
                .phoneNumber(store.getPhoneNumber())
                .build();
    }
}
