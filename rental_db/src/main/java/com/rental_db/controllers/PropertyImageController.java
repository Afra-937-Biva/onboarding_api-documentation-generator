package com.rental_db.controllers;

import com.rental_db.dto.PropertyImageDTO;
import com.rental_db.entities.PropertyImage;
import com.rental_db.services.PropertyImageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/property-images")
public class PropertyImageController {

    private final PropertyImageService service;

    public PropertyImageController(PropertyImageService service) {
        this.service = service;
    }

    @PostMapping
    public PropertyImage upload(@RequestBody PropertyImageDTO dto) {
        return service.addImage(dto);
    }
}

