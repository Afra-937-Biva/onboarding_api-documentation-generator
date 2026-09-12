package com.rental_db.repositories;

import com.rental_db.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    java.util.List<Contact> findByActiveStatus(Integer activeStatus);
}

