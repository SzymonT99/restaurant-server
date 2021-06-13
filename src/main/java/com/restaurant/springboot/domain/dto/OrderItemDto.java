package com.restaurant.springboot.domain.dto;

import java.util.List;
import java.util.Objects;

public class OrderItemDto {

    private String itemName;
    private List<String> ingredients;
    private Float price;
    private Double rate;
    private String menuItemImage;
    private Integer quantity;
    private Long detailsId;

    public OrderItemDto() {
    }

    public OrderItemDto(String itemName, List<String> ingredients, Float price, Double rate, String menuItemImage, Integer quantity, Long detailsId) {
        this.itemName = itemName;
        this.ingredients = ingredients;
        this.price = price;
        this.rate = rate;
        this.menuItemImage = menuItemImage;
        this.quantity = quantity;
        this.detailsId = detailsId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
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

    public Long getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Long detailsId) {
        this.detailsId = detailsId;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "itemName='" + itemName + '\'' +
                ", ingridients=" + ingredients +
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
        return Objects.equals(getItemName(), that.getItemName()) && Objects.equals(getIngredients(),
                that.getIngredients()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getRate(),
                that.getRate()) && Objects.equals(getMenuItemImage(), that.getMenuItemImage())
                && Objects.equals(getQuantity(), that.getQuantity());
    }

}
