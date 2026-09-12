package com.rental_db.dto;

public class PropertyImageDTO {
    private Long propertyId;
    private String imageUrl;

    public PropertyImageDTO() {
    }

    public PropertyImageDTO(Long propertyId, String imageUrl) {
        this.propertyId = propertyId;
        this.imageUrl = imageUrl;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

