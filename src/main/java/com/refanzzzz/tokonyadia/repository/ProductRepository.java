package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
