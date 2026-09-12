package com.rental_db.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uniqueId;
    private String title;
    private String description;
    private Double price;
    private Double securityDeposit;

    @ManyToOne
    @JoinColumn(name = "property_type_id")
    private PropertyType propertyType;

    private Integer bedroom;
    private Integer bathroom;
    private Integer balcony;
    private Double area;
    private String amenities;
    private String address;
    private String location;
    private Boolean isAvailable;
    private Integer activeStatus = 1;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Property() {
    }

    public Property(Long id, String uniqueId, String title, String description, Double price, Double securityDeposit, PropertyType propertyType, Integer bedroom, Integer bathroom, Integer balcony, Double area, String amenities, String address, String location, Boolean isAvailable, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.securityDeposit = securityDeposit;
        this.propertyType = propertyType;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.balcony = balcony;
        this.area = area;
        this.amenities = amenities;
        this.address = address;
        this.location = location;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(Double securityDeposit) {
        this.securityDeposit = securityDeposit;
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

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
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

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Integer activeStatus) {
        this.activeStatus = activeStatus;
    }
}

