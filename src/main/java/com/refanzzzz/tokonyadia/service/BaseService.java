package com.refanzzzz.tokonyadia.service;

import org.springframework.data.domain.Page;

public interface BaseService<TRequest, TResponse, TEntity> {
    Page<TResponse> getAll(TRequest request);

    TResponse getById(String id);

    TResponse create(TRequest request);

    TEntity getOne(String id);

    void remove(String id);

    TResponse update(String id, TRequest request);
}
