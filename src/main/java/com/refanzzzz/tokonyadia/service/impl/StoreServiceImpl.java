package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.StoreRequest;
import com.refanzzzz.tokonyadia.dto.response.StoreResponse;
import com.refanzzzz.tokonyadia.entity.Store;
import com.refanzzzz.tokonyadia.repository.StoreRepository;
import com.refanzzzz.tokonyadia.service.StoreService;
import com.refanzzzz.tokonyadia.specification.StoreSpecification;
import com.refanzzzz.tokonyadia.util.MapperUtil;
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

        return storePage.map(MapperUtil::toStoreProductResponse);
    }

    @Override
    public StoreResponse getById(String id) {
        Store store = getOne(id);
        return MapperUtil.toStoreResponse(store);
    }

    @Override
    public StoreResponse create(StoreRequest request) {
        Store store = Store.builder()
                .name(request.getName())
                .noSiup(request.getNoSiup())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .build();

        storeRepository.saveAndFlush(store);

        return MapperUtil.toStoreResponse(store);
    }

    @Override
    public void remove(String id) {
        Store store = getOne(id);
        storeRepository.delete(store);
    }

    @Override
    public StoreResponse update(String id, StoreRequest request) {
        Store store = getOne(id);

        store.setName(request.getName());
        store.setNoSiup(request.getNoSiup());
        store.setPhoneNumber(request.getPhoneNumber());
        store.setAddress(request.getAddress());

        storeRepository.saveAndFlush(store);
        return MapperUtil.toStoreResponse(store);
    }

    @Override
    public Store getOne(String id) {
        return storeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store is not found!"));
    }
}
