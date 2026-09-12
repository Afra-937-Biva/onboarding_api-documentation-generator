package com.rental_db.dto;

public class PropertyDTO {

    private String title;
    private String description;
    private Double price;
    private Double securityDeposit;
    private Long propertyTypeId;

    private Integer bedroom;
    private Integer bathroom;
    private Integer balcony;
    private Double area;
    private String amenities;
    private String address;
    private String location;

    public PropertyDTO() {
    }

    public PropertyDTO(String title, String description, Double price, Double securityDeposit, Long propertyTypeId, Integer bedroom, Integer bathroom, Integer balcony, Double area, String amenities, String address, String location) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.securityDeposit = securityDeposit;
        this.propertyTypeId = propertyTypeId;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.balcony = balcony;
        this.area = area;
        this.amenities = amenities;
        this.address = address;
        this.location = location;
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

    public Long getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(Long propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
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
}

