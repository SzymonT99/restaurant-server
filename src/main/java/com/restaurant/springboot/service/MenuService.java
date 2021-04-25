package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.MenuDto;

import java.util.List;

public interface MenuService {

    List<MenuDto> getMenuByCategoryId(Long categoryId);

    List<MenuDto> getSpecialOfferMenu();

    List<MenuDto> getUserFavoriteMenu(Long userId);
}
