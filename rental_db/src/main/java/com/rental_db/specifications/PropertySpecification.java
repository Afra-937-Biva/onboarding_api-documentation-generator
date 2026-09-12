package com.rental_db.specifications;

import com.rental_db.entities.Property;
import com.rental_db.entities.PropertyType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {

    public static Specification<Property> filterProperties(
            Long typeId,
            String location,
            Double minPrice,
            Double maxPrice,
            String keyword
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always filter by activeStatus = 1
            predicates.add(criteriaBuilder.equal(root.get("activeStatus"), 1));

            // Filter by Property Type
            if (typeId != null) {
                Join<Property, PropertyType> typeJoin = root.join("propertyType");
                predicates.add(criteriaBuilder.equal(typeJoin.get("id"), typeId));
            }

            // Filter by Location (search in both location and address fields)
            if (location != null && !location.trim().isEmpty()) {
                String likePattern = "%" + location.toLowerCase() + "%";
                Predicate locationPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), likePattern)
                );
                predicates.add(locationPredicate);
            }

            // Filter by Minimum Price
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            // Filter by Maximum Price
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            // Filter by Keyword (search in title and description)
            if (keyword != null && !keyword.trim().isEmpty()) {
                String likePattern = "%" + keyword.toLowerCase() + "%";
                Predicate keywordPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
                );
                predicates.add(keywordPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
