package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User purchaser;

    @ManyToMany
    @JoinTable(
            name = "menu_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    Set<Menu> menuItems;

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

    public Integer getOrderId() {
        return orderId;
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

    public User getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    public Set<Menu> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(Set<Menu> menuItems) {
        this.menuItems = menuItems;
    }
}
