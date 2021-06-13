package com.restaurant.springboot.domain.dto;

import java.util.List;

public class MenuDto {

    private Long menuId;
    private String itemName;
    private List<String> ingredients;
    private Float price;
    private Double rate;
    private String menuItemImage;
    private Long detailsId;
    private String categoryName;

    public MenuDto() {
    }

    public MenuDto(String itemName, List<String> ingredients, Float price, Double rate, String menuItemImage, Long detailsId, String categoryName) {
        this.itemName = itemName;
        this.ingredients = ingredients;
        this.price = price;
        this.rate = rate;
        this.menuItemImage = menuItemImage;
        this.detailsId = detailsId;
        this.categoryName = categoryName;
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

    public Long getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Long detailsId) {
        this.detailsId = detailsId;
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
}
