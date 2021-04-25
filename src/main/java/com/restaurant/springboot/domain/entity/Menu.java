package com.restaurant.springboot.domain.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @NotNull
    @Column(name = "item_name")
    private String itemName;

    @NotNull
    private Float price;

    @NotNull
    private String ingridients;

    @NotNull
    @Column(name = "menu_item_image")
    private String menuItemImage;

    @Column(name = "special_offer")
    private boolean specialOffer;

    @OneToOne(mappedBy = "menu", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Details details;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @Transient
    private Long categoryId;

    @ManyToMany(mappedBy = "likedMenu")
    @JsonIgnore
    Set<User> users;

    @ManyToMany(mappedBy = "menuItems")
    @JsonIgnore
    Set<Order> orders;

    public Menu() {
    }

    public Menu(@NotNull String itemName, @NotNull Float price, @NotNull String ingridients, @NotNull String menuItemImage, boolean specialOffer) {
        this.itemName = itemName;
        this.price = price;
        this.ingridients = ingridients;
        this.menuItemImage = menuItemImage;
        this.specialOffer = specialOffer;
    }

    public Long getMenuId() {
        return menuId;
    }

    public Category getCategory() {
        return category;
    }

    public Long getCategoryId() {
        return getCategory().getCategoryId();
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getMenuItemImage() {
        return menuItemImage;
    }

    public void setMenuItemImage(String menuItemImage) {
        this.menuItemImage = menuItemImage;
    }

    public boolean isSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(boolean specialOffer) {
        this.specialOffer = specialOffer;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public String getIngridients() {
        return ingridients;
    }

    public void setIngridients(String ingridients) {
        this.ingridients = ingridients;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
