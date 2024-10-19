package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entity.Cart;
import com.refanzzzz.tokonyadia.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartRepository extends JpaRepository<Cart, String>, JpaSpecificationExecutor<Cart> {
    Page<Cart> findCartsByCustomer(Pageable pageable, Customer customer);

    Cart getCartByCustomer(Customer customer);
}
