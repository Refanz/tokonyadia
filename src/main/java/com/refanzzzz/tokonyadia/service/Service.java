package com.refanzzzz.tokonyadia.service;

import org.springframework.data.domain.Page;

public interface Service<T, U> {
    Page<T> getAll(Integer page, Integer size, String sort);

    T getById(String id);

    T insert(U data);

    void remove(String id);

    T update(String id, U data);
}
