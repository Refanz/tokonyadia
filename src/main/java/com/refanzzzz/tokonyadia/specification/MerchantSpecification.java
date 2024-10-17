package com.refanzzzz.tokonyadia.specification;

import com.refanzzzz.tokonyadia.dto.request.MerchantRequest;
import com.refanzzzz.tokonyadia.entity.Merchant;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MerchantSpecification {
    public static Specification<Merchant> getMerchantSpecification(MerchantRequest merchantRequest) {

        String queryRequest = merchantRequest.getSearchRequest().getQuery();

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (queryRequest != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), queryRequest.toLowerCase() + "%"));
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
