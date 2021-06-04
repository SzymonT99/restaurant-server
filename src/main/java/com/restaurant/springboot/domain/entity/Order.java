package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.springboot.domain.model.Status;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "total_price")
    private Float totalPrice;

    @Column(name = "delivery_place")
    private String deliveryPlace;

    private String street;

    @Column(name = "postal_code")
    private String postalCode;

    @Temporal(TemporalType.TIMESTAMP)
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
    private List<MenuOrders> menuOrders;

    public Order() {
    }

    public Order(@NotNull Float totalPrice, @NotNull String deliveryPlace, @NotNull String street,
                 @NotNull String postalCode, Date date, Status status) {
        this.totalPrice = totalPrice;
        this.deliveryPlace = deliveryPlace;
        this.street = street;
        this.postalCode = postalCode;
        this.date = date;
        this.status = status;
    }

    public Long getOrderId() {
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

    public List<MenuOrders> getMenuOrders() {
        return menuOrders;
    }

    public void setMenuOrders(List<MenuOrders> menuOrders) {
        this.menuOrders = menuOrders;
    }


}
