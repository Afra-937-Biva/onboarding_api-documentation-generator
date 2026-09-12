package com.rental_db.controllers;

import com.rental_db.dto.PropertyDTO;
import com.rental_db.entities.Property;
import com.rental_db.services.PropertyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(PropertyService service) {
        this.service = service;
    }

    @PostMapping
    public Property create(@RequestBody PropertyDTO dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<Property> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<Property> searchProperties(
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String keyword
    ) {
        return service.searchProperties(typeId, location, minPrice, maxPrice, keyword);
    }

    @GetMapping("/{id}")
    public Property getById(@PathVariable Long id) {
        return service.getAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Property update(@PathVariable Long id, @RequestBody PropertyDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted";
    }
}

