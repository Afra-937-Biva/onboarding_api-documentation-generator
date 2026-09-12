package com.rental_db.dto;

public class ContactDTO {

    private String fullName;
    private String email;
    private String contactNumber;
    private String description;
    private Long propertyTypeId;

    private Integer bedroom;
    private Integer bathroom;
    private Integer balcony;
    private Double area;
    private Double price;
    private String address;
    private String location;
    private String imageTempUrls;

    public ContactDTO() {
    }

    public ContactDTO(String fullName, String email, String contactNumber, String description, Long propertyTypeId, Integer bedroom, Integer bathroom, Integer balcony, Double area, Double price, String address, String location, String imageTempUrls) {
        this.fullName = fullName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.description = description;
        this.propertyTypeId = propertyTypeId;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.balcony = balcony;
        this.area = area;
        this.price = price;
        this.address = address;
        this.location = location;
        this.imageTempUrls = imageTempUrls;
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
}

