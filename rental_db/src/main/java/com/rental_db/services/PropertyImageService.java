package com.rental_db.services;

import com.rental_db.dto.PropertyImageDTO;
import com.rental_db.entities.Property;
import com.rental_db.entities.PropertyImage;
import com.rental_db.repositories.PropertyImageRepository;
import com.rental_db.repositories.PropertyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PropertyImageService {

    private final PropertyImageRepository repo;
    private final PropertyRepository propertyRepo;

    public PropertyImageService(PropertyImageRepository repo, PropertyRepository propertyRepo) {
        this.repo = repo;
        this.propertyRepo = propertyRepo;
    }

    public PropertyImage addImage(PropertyImageDTO dto) {

        Property property = propertyRepo.findById(dto.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        PropertyImage img = new PropertyImage();
        img.setProperty(property);
        img.setImageUrl(dto.getImageUrl());
        img.setCreatedAt(LocalDateTime.now());

        return repo.save(img);
    }
    public java.util.List<PropertyImage> findByPropertyId(Long propertyId) {
        return repo.findByPropertyIdAndActiveStatus(propertyId, 1);
    }

    public void delete(Long id) {
        PropertyImage img = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        img.setActiveStatus(0);
        repo.save(img);
    }
}

