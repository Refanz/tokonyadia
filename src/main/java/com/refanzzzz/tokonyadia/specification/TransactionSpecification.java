package com.refanzzzz.tokonyadia.specification;

import com.refanzzzz.tokonyadia.dto.request.transaction.TransactionSearchRequest;
import com.refanzzzz.tokonyadia.entity.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> getTransactionSpecification(TransactionSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getMinTransactionDate() != null && request.getMaxTransactionDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("transaction_date"), request.getMinTransactionDate(), request.getMaxTransactionDate()));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
