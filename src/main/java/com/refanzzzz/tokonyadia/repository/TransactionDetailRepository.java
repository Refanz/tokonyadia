package com.refanzzzz.tokonyadia.repository;

import com.refanzzzz.tokonyadia.entitiy.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
}
