package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.LikedMenuDto;
import com.restaurant.springboot.domain.dto.MenuDto;
import com.restaurant.springboot.domain.entity.Details;
import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.domain.entity.Review;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.mapper.MenuLikedMapper;
import com.restaurant.springboot.domain.mapper.MenuListMapper;
import com.restaurant.springboot.domain.repository.MenuRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final MenuListMapper menuListMapper;
    private final MenuLikedMapper menuLikedMapper;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository, UserRepository userRepository, MenuListMapper menuListMapper,
                           MenuLikedMapper menuLikedMapper) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
        this.menuListMapper = menuListMapper;
        this.menuLikedMapper = menuLikedMapper;
    }

    @Override
    public List<MenuDto> getMenuByCategoryId(Long categoryId) {

        List<Menu> menuOfCategory = new ArrayList<>();

        List<Menu> menuList = menuRepository.findAll();
        for(Menu menuItem : menuList) {
           if(menuItem.getCategory().getCategoryId() == categoryId) {
               menuOfCategory.add(menuItem);
           }
        }
        return menuListMapper.mapToDto(menuOfCategory);
    }

    @Override
    public List<MenuDto> getSpecialOfferMenu() {

        List<Menu> menuOfSpecialOffers = menuRepository.findAllBySpecialOffer(true);
        return menuListMapper.mapToDto(menuOfSpecialOffers);
    }

    @Override
    public List<LikedMenuDto> getUserLikedMenu(Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        List<Menu> userMenuFavouriteList = Objects.requireNonNull(user).getLikedMenu();

        return menuLikedMapper.mapToDto(userMenuFavouriteList);
    }

    @Override
    public List<MenuDto> getUserFavouriteMenu(Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        List<Menu> userMenuFavouriteList = Objects.requireNonNull(user).getLikedMenu();
        return menuListMapper.mapToDto(userMenuFavouriteList);
    }

    @Override
    public void saveAsFavourite(Long menuItemId, Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        List<Menu> userMenuFavouriteList = Objects.requireNonNull(user).getLikedMenu();

        Menu newLikedMenuItem = menuRepository.findById(menuItemId).orElse(null);

        userMenuFavouriteList.add(newLikedMenuItem);

        user.setLikedMenu(userMenuFavouriteList);
        userRepository.save(user);
    }

    @Override
    public void deleteFromFavourite(Long menuItemId, Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        List<Menu> userMenuFavouriteList = user.getLikedMenu();

        userMenuFavouriteList.removeIf(menuItem -> menuItem.getMenuId().equals(menuItemId));

        user.setLikedMenu(userMenuFavouriteList);
        userRepository.save(user);
    }

}
