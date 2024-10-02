package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.entitiy.Product;
import com.refanzzzz.tokonyadia.entitiy.Store;

import java.util.List;

public interface StoreService {

    List<Store> getAllStore();

    Store getStoreById(String id);

    Store addStore(Store store);

    Store updateStore(String id, Store store);

    String deleteStoreById(String id);
}
