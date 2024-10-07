package com.refanzzzz.tokonyadia.service;

import org.springframework.data.domain.Page;

public interface Service<T, U, V> {
    Page<T> getAll(U request);

    T getById(String id);

    T insert(U data);

    V getOne(String id);

    void remove(String id);

    T update(String id, U data);
}
