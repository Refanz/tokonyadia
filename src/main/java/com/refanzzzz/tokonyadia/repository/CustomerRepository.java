package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entitiy.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
