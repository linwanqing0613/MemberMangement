package com.example.membermangement.dto;

import com.example.membermangement.model.Member;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Date;

public class MemberSpecifications {
    public static Specification<Member> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction(); // 返回一個永真條件
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Member> hasBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance) {
        return (root, query, criteriaBuilder) -> {
            if (minBalance == null && maxBalance == null) {
                return criteriaBuilder.conjunction();
            } else if (minBalance == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("balance"), maxBalance);
            } else if (maxBalance == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("balance"), minBalance);
            } else {
                return criteriaBuilder.between(root.get("balance"), minBalance, maxBalance);
            }
        };
    }

    public static Specification<Member> hasBirthDate(Date birth_date) {
        return (root, query, criteriaBuilder) -> {
            if (birth_date == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("birth_date"), birth_date);
        };
    }

    public static Specification<Member> hasAge(Integer age) {
        return (root, query, criteriaBuilder) -> {
            if (age == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("age"), age);
        };
    }
}
