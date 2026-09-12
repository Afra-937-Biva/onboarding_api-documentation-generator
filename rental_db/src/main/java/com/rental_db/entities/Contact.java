package com.rental_db.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uniqueId;
    private String fullName;
    private String email;
    private String contactNumber;
    private String description;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    private Integer bedroom;
    private Integer bathroom;
    private Integer balcony;
    private Double area;
    private Double price;
    private String address;
    private String location;

    @Column(columnDefinition = "TEXT")
    private String imageTempUrls;

    private String status;
    private Integer activeStatus = 1;
    private LocalDateTime createdAt;

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Contact() {
    }

    public Contact(Long id, String uniqueId, String fullName, String email, String contactNumber, String description, PropertyType propertyType, Integer bedroom, Integer bathroom, Integer balcony, Double area, Double price, String address, String location, String imageTempUrls, String status, LocalDateTime createdAt) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.fullName = fullName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.description = description;
        this.propertyType = propertyType;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.balcony = balcony;
        this.area = area;
        this.price = price;
        this.address = address;
        this.location = location;
        this.imageTempUrls = imageTempUrls;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getBedroom() {
        return bedroom;
    }

    public void setBedroom(Integer bedroom) {
        this.bedroom = bedroom;
    }

    public Integer getBathroom() {
        return bathroom;
    }

    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    public Integer getBalcony() {
        return balcony;
    }

    public void setBalcony(Integer balcony) {
        this.balcony = balcony;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageTempUrls() {
        return imageTempUrls;
    }

    public void setImageTempUrls(String imageTempUrls) {
        this.imageTempUrls = imageTempUrls;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

