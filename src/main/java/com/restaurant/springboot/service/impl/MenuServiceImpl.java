package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.MenuDto;
import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.mapper.MenuListMapper;
import com.restaurant.springboot.domain.repository.MenuRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final MenuListMapper menuListMapper;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, UserRepository userRepository, MenuListMapper menuListMapper) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
        this.menuListMapper = menuListMapper;
    }

    @Override
    public List<MenuDto> getMenuByCategoryId(Long categoryId) {

        List<Menu> menuOfCategory = menuRepository.findAllByCategoryId(categoryId);
        return menuListMapper.mapToDto(menuOfCategory);
    }

    @Override
    public List<MenuDto> getSpecialOfferMenu() {

        List<Menu> menuOfSpecialOffers = menuRepository.findAllBySpecialOffer(true);
        return menuListMapper.mapToDto(menuOfSpecialOffers);
    }

    @Override
    public List<MenuDto> getUserFavoriteMenu(Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        List<Menu> userMenuFavouriteList = (List<Menu>) user.getLikedMenu();
        return menuListMapper.mapToDto(userMenuFavouriteList);
    }
}
