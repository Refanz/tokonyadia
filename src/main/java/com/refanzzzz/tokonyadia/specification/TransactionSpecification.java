package com.refanzzzz.tokonyadia.specification;

import com.refanzzzz.tokonyadia.dto.request.TransactionRequest;
import com.refanzzzz.tokonyadia.entity.Transaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> getTransactionSpecification(TransactionRequest request) {
        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (request.getMinTransactionDate() != null && request.getMaxTransactionDate() != null) {
                    predicates.add(criteriaBuilder.between(root.get("transaction_date"), request.getMinTransactionDate(), request.getMaxTransactionDate()));
                }

                return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
            }
        };
    }
}
