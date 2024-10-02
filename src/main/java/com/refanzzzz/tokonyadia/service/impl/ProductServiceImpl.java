package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.entitiy.Product;
import com.refanzzzz.tokonyadia.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> getAllProduct() {
        return List.of();
    }

    @Override
    public Product getProductById(String id) {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(String id, Product product) {
        return null;
    }

    @Override
    public String deleteProductById(String id) {
        return "";
    }
}
