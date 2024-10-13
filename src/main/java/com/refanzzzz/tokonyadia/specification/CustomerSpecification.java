package com.refanzzzz.tokonyadia.specification;

import com.refanzzzz.tokonyadia.dto.request.CustomerRequest;
import com.refanzzzz.tokonyadia.entity.Customer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getCustomerSpecification(CustomerRequest request) {
        return new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();

                if (request.getQuery() != null) {
                    Predicate predicate = criteriaBuilder.like(root.get("name"), request.getQuery() + "%");
                    predicates.add(predicate);
                }

                return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
            }
        };
    }
}
