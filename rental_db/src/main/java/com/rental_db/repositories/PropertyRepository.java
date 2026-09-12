package com.rental_db.repositories;

import com.rental_db.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    java.util.List<Property> findByActiveStatus(Integer activeStatus);
}

