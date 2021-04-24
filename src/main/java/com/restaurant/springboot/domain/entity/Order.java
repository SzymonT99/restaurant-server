package com.restaurant.springboot.domain.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer tableId;

    @NotNull
    @Column(name = "total_price")
    private Float totalPrice;

    @NotNull
    @Column(name = "delivery_place")
    private String deliveryPlace;

    @NotNull
    private String street;

    @NotNull
    @Column(name = "postal_code")
    private String postalCode;

    @DateTimeFormat
    @NotNull
    private Date date;

    @NotNull
    private boolean status;

    public Order() {
    }

    public Order(@NotNull Float totalPrice, @NotNull String deliveryPlace,
                 @NotNull String street, @NotNull String postalCode, @NotNull Date date, @NotNull boolean status) {
        this.totalPrice = totalPrice;
        this.deliveryPlace = deliveryPlace;
        this.street = street;
        this.postalCode = postalCode;
        this.date = date;
        this.status = status;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryPlace() {
        return deliveryPlace;
    }

    public void setDeliveryPlace(String deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
