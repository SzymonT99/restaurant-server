package com.restaurant.springboot.domain.dto;

import java.util.List;

public class OrderItemDto {

    private String itemName;
    private List<String> ingridients;
    private Float price;
    private Integer quantity;
    private String menuItemImage;
    private Integer rate;

    public OrderItemDto() {
    }

    public OrderItemDto(String itemName, List<String> ingridients, Float price, Integer quantity, String menuItemImage, Integer rate) {
        this.itemName = itemName;
        this.ingridients = ingridients;
        this.price = price;
        this.quantity = quantity;
        this.menuItemImage = menuItemImage;
        this.rate = rate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getIngridients() {
        return ingridients;
    }

    public void setIngridients(List<String> ingridients) {
        this.ingridients = ingridients;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMenuItemImage() {
        return menuItemImage;
    }

    public void setMenuItemImage(String menuItemImage) {
        this.menuItemImage = menuItemImage;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
