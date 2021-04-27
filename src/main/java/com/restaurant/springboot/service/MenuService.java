package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.LikedMenuDto;
import com.restaurant.springboot.domain.dto.MenuDto;

import java.util.List;

public interface MenuService {

    List<MenuDto> getMenuByCategoryId(Long categoryId);

    List<MenuDto> getSpecialOfferMenu();

    List<LikedMenuDto> getUserLikedMenuId(Long userId);

    List<MenuDto> getUserFavouriteMenu(Long userId);

    void saveAsFavourite(Long menuItemId, Long userId);

    void deleteFromFavourite(Long menuItemId, Long userId);

}
