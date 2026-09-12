package com.rental_db.repositories;

import com.rental_db.entities.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {
    java.util.List<PropertyImage> findByPropertyId(Long propertyId);
    java.util.List<PropertyImage> findByActiveStatus(Integer activeStatus);
    java.util.List<PropertyImage> findByPropertyIdAndActiveStatus(Long propertyId, Integer activeStatus);
}

