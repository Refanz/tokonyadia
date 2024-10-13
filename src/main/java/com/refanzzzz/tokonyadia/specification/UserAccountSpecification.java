package com.refanzzzz.tokonyadia.specification;

import com.refanzzzz.tokonyadia.dto.request.UserAccountRequest;
import com.refanzzzz.tokonyadia.entity.UserAccount;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserAccountSpecification {
    public static Specification<UserAccount> getUserAccountSpecification(UserAccountRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (request.getQuery() != null) {
                predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), request.getQuery().toLowerCase() + "%"));
            }

            return query.where(predicateList.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
