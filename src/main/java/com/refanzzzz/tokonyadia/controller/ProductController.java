package com.refanzzzz.tokonyadia.controller;

import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.entitiy.Product;
import com.refanzzzz.tokonyadia.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    /* @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    } */

    @PostMapping
    public Product addNewProduct(@RequestBody ProductRequest productRequest) {
        return productService.addNewProduct(productRequest);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable String id) {
        return productService.deleteProductById(id);
    }
}
