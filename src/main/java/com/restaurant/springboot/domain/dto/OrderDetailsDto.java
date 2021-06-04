package com.restaurant.springboot.domain.dto;

public class OrderDetailsDto {

    private String town;
    private String postalCode;
    private String street;
    private String paymentMethod;

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String town, String postalCode, String street, String paymentMethod) {
        this.town = town;
        this.postalCode = postalCode;
        this.street = street;
        this.paymentMethod = paymentMethod;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
