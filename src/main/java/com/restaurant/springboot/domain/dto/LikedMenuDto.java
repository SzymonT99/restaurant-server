package com.restaurant.springboot.domain.dto;

public class LikedMenuDto {

    private Long menuId;
    private String menuItemName;

    public LikedMenuDto() {
    }

    public LikedMenuDto(Long menuId, String menuItemName) {
        this.menuId = menuId;
        this.menuItemName = menuItemName;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }
}
