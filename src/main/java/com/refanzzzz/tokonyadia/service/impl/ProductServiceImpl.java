package com.refanzzzz.tokonyadia.service.impl;

import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.entitiy.Product;
import com.refanzzzz.tokonyadia.entitiy.Store;
import com.refanzzzz.tokonyadia.repository.ProductRepository;
import com.refanzzzz.tokonyadia.repository.StoreRepository;
import com.refanzzzz.tokonyadia.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return productOptional.get();
        }

        throw new RuntimeException("Product tidak ada!");
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product addNewProduct(ProductRequest productRequest) {
        Optional<Store> optionalStore = storeRepository.findById(productRequest.getStoreId());

        if (optionalStore.isPresent()) {
            Store store = optionalStore.get();
            Product newProduct = new Product(
                    null,
                    productRequest.getName(),
                    productRequest.getDescription(),
                    productRequest.getPrice(),
                    productRequest.getStock(),
                    store);

            productRepository.save(newProduct);
        }

        throw new RuntimeException("Store tidak ada!");
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            Product updateProduct = productOptional.get();
            updateProduct.setName(product.getName());
            updateProduct.setStock(product.getStock());
            updateProduct.setPrice(product.getPrice());
            updateProduct.setDescription(product.getDescription());

            return productRepository.save(updateProduct);
        }

        throw new RuntimeException("Product tidak ada!");
    }

    @Override
    public String deleteProductById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            productRepository.delete(productOptional.get());
            return "Data product berhasil dihapus dengan id: " + id;
        }

        throw new RuntimeException("Product tidak ada!");
    }
}
