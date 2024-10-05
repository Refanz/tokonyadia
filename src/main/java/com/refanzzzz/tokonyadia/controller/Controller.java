package com.refanzzzz.tokonyadia.controller;

import org.springframework.http.ResponseEntity;

public interface Controller<T, U> {

    ResponseEntity<T> getAll(String query, Integer page, Integer size, String sort);

    ResponseEntity<T> getById(String id);

    ResponseEntity<T> insert(U request);

    ResponseEntity<T> remove(String id);

    ResponseEntity<T> update(String id, U request);
}
