package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
