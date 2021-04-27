package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.springboot.domain.model.Status;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User purchaser;

    @OneToMany(mappedBy = "orderItem", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<MenuOrders> menuOrders;

    public Order() {
    }

    public Order(@NotNull Float totalPrice, @NotNull String deliveryPlace, @NotNull String street,
                 @NotNull String postalCode, @NotNull Date date, Status status) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

    public Set<MenuOrders> getMenuOrders() {
        return menuOrders;
    }

    public void setMenuOrders(Set<MenuOrders> menuOrders) {
        this.menuOrders = menuOrders;
    }


}
