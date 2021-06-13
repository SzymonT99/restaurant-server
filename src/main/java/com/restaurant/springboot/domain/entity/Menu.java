package com.restaurant.springboot.domain.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
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
    private String ingredients;

    @NotNull
    @Column(name = "average_rate")
    private Double averageRate;

    @NotNull
    @Column(name = "menu_item_image")
    private String menuItemImage;

    @Column(name = "special_offer")
    private boolean specialOffer;

    @OneToOne(mappedBy = "menu", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Details details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    @Transient
    private Long categoryId;

    @ManyToMany(mappedBy = "likedMenu")
    @JsonIgnore
    private List<User> likedUsers;

    @OneToMany(mappedBy = "menuItem", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<MenuOrders> menuOrders;

    public Menu() {
    }

    public Menu(@NotNull String itemName, @NotNull Float price, @NotNull String ingredients,
                @NotNull Double averageRate, @NotNull String menuItemImage, boolean specialOffer) {
        this.itemName = itemName;
        this.price = price;
        this.ingredients = ingredients;
        this.averageRate = averageRate;
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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(List<User> users) {
        this.likedUsers = users;
    }

    public Set<MenuOrders> getMenuOrders() {
        return menuOrders;
    }

    public void setMenuOrders(Set<MenuOrders> menuOrders) {
        this.menuOrders = menuOrders;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }
}
