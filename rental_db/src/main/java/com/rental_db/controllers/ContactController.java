package com.rental_db.controllers;

import com.rental_db.dto.ContactDTO;
import com.rental_db.entities.Contact;
import com.rental_db.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @PostMapping
    public Contact submit(@RequestBody ContactDTO dto) {
        return service.create(dto);
    }
}

