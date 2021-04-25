package com.restaurant.springboot.domain.dto;

import java.util.List;

public class MenuDto {

    private Long menuId;
    private String itemName;
    private List<String> ingridients;
    private Float price;
    private Integer rate;
    private String menuItemImage;
    private Long detailsId;
    private String categoryName;

    public MenuDto() {
    }

    public MenuDto(String itemName, List<String> ingridients, Float price, Integer rate, String menuItemImage, Long detailsId, String categoryName) {
        this.itemName = itemName;
        this.ingridients = ingridients;
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

    public List<String> getIngridients() {
        return ingridients;
    }

    public void setIngridients(List<String> ingridients) {
        this.ingridients = ingridients;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
