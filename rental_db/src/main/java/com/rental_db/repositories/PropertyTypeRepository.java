package com.rental_db.repositories;

import com.rental_db.entities.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyTypeRepository extends JpaRepository<PropertyType, Long> {
    java.util.List<PropertyType> findByActiveStatus(Integer activeStatus);
}

