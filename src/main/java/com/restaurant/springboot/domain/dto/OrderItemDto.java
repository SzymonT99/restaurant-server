package com.restaurant.springboot.domain.dto;

import java.util.List;
import java.util.Objects;

public class OrderItemDto {

    private String itemName;
    private List<String> ingridients;
    private Float price;
    private Double rate;
    private String menuItemImage;
    private Integer quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(String itemName, List<String> ingridients, Float price, Integer quantity, String menuItemImage, Double rate) {
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "itemName='" + itemName + '\'' +
                ", ingridients=" + ingridients +
                ", price=" + price +
                ", rate=" + rate +
                ", menuItemImage='" + menuItemImage + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemDto)) return false;
        OrderItemDto that = (OrderItemDto) o;
        return Objects.equals(getItemName(), that.getItemName()) && Objects.equals(getIngridients(),
                that.getIngridients()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getRate(),
                that.getRate()) && Objects.equals(getMenuItemImage(), that.getMenuItemImage())
                && Objects.equals(getQuantity(), that.getQuantity());
    }

}
