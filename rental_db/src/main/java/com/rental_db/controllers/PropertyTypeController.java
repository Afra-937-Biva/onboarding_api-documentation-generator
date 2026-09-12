package com.rental_db.controllers;

import com.rental_db.dto.PropertyTypeDTO;
import com.rental_db.entities.PropertyType;
import com.rental_db.services.PropertyTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property-types")
public class PropertyTypeController {

    private final PropertyTypeService service;

    public PropertyTypeController(PropertyTypeService service) {
        this.service = service;
    }

    @PostMapping
    public PropertyType create(@RequestBody PropertyTypeDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<PropertyType> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public PropertyType update(@PathVariable Long id, @RequestBody PropertyTypeDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted successfully";
    }
}
