package com.refanzzzz.tokonyadia.specification;

import com.refanzzzz.tokonyadia.dto.request.ProductRequest;
import com.refanzzzz.tokonyadia.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> getProductSpecification(ProductRequest request) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();

                if (request.getQuery() != null) {
                    Predicate predicate = criteriaBuilder.like(root.get("name"), request.getQuery() + "%");
                    predicateList.add(predicate);
                }

                return query.where(predicateList.toArray(new Predicate[]{})).getRestriction();
            }
        };
    }
}
