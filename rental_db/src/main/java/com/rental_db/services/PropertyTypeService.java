package com.rental_db.services;

import com.rental_db.dto.PropertyTypeDTO;
import com.rental_db.entities.PropertyType;
import com.rental_db.repositories.PropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyTypeService {

    private final PropertyTypeRepository repo;

    public PropertyTypeService(PropertyTypeRepository repo) {
        this.repo = repo;
    }

    public PropertyType create(PropertyTypeDTO dto) {
        PropertyType type = new PropertyType();
        type.setName(dto.getName());
        return repo.save(type);
    }

    public List<PropertyType> getAll() {
        return repo.findByActiveStatus(1);
    }

    public PropertyType update(Long id, PropertyTypeDTO dto) {
        PropertyType type = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        type.setName(dto.getName());
        return repo.save(type);
    }

    public void delete(Long id) {
        PropertyType type = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        type.setActiveStatus(0);
        repo.save(type);
    }

    public void save(PropertyType type) {
        if (type.getActiveStatus() == null) {
            type.setActiveStatus(1);
        }
        repo.save(type);
    }
}

