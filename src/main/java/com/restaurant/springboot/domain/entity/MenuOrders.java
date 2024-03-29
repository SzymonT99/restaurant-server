package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "menu_orders")
public class MenuOrders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order orderItem;

    public MenuOrders() {
    }

    public MenuOrders(Menu menuItem, Order orderItem) {
        this.menuItem = menuItem;
        this.orderItem = orderItem;
    }

    public Long getId() {
        return id;
    }

    public Menu getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Menu menuItem) {
        this.menuItem = menuItem;
    }

    public Order getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Order orderItem) {
        this.orderItem = orderItem;
    }
}
