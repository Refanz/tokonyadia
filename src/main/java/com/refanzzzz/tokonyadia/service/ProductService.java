package com.refanzzzz.tokonyadia.service;

import com.refanzzzz.tokonyadia.entitiy.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    Product getProductById(String id);

    Product addProduct(Product product);

    Product updateProduct(String id, Product product);

    String deleteProductById(String id);
}
