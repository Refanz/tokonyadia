package com.refanzzzz.tokonyadia.service;

import org.springframework.data.domain.Page;

/**
 *
 * @param <T> Return Type
 * @param <U> Parameter Type
 * @param <V> Optional Return Type
 */
public interface Service<T, U, V> {
    Page<T> getAll(U request);

    T getById(String id);

    T create(U data);

    V getOne(String id);

    void remove(String id);

    T update(String id, U data);
}
