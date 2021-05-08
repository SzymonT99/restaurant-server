package com.restaurant.springboot.domain.dto;

import com.restaurant.springboot.domain.entity.Details;

import java.util.List;

public class DetailsDto {

    private Long menuId;
    private String itemName;
    private List<String> ingredients;
    private Float price;
    private Double rate;
    private String menuItemImage;
    private String categoryName;
    private Details details;

    public DetailsDto() {
    }

    public DetailsDto(Long menuId, String itemName, List<String> ingredients, Float price, Double rate,
                      String menuItemImage, String categoryName, Details details) {
        this.menuId = menuId;
        this.itemName = itemName;
        this.ingredients = ingredients;
        this.price = price;
        this.rate = rate;
        this.menuItemImage = menuItemImage;
        this.categoryName = categoryName;
        this.details = details;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getMenuItemImage() {
        return menuItemImage;
    }

    public void setMenuItemImage(String menuItemImage) {
        this.menuItemImage = menuItemImage;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}

