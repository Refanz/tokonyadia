package com.refanzzzz.tokonyadia.controller;

import org.springframework.http.ResponseEntity;

public interface Controller<TResponse, TRequest> {

    ResponseEntity<TResponse> getAll(String query, Integer page, Integer size, String sort);

    ResponseEntity<TResponse> getById(String id);

    ResponseEntity<TResponse> create(TRequest request);

    ResponseEntity<TResponse> remove(String id);

    ResponseEntity<TResponse> update(String id, TRequest request);
}
