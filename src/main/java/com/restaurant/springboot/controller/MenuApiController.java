package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.dto.DetailsDto;
import com.restaurant.springboot.domain.dto.LikedMenuDto;
import com.restaurant.springboot.domain.dto.MenuDto;
import com.restaurant.springboot.domain.entity.Category;

import com.restaurant.springboot.service.CategoryService;
import com.restaurant.springboot.service.DetailsService;
import com.restaurant.springboot.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class MenuApiController {

    private final CategoryService categoryService;
    private final MenuService menuService;
    private final DetailsService detailsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuApiController.class);

    @Autowired
    public MenuApiController(CategoryService categoryService, MenuService menuService, DetailsService detailsService) {
        this.categoryService = categoryService;
        this.menuService = menuService;
        this.detailsService = detailsService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {

        return new ResponseEntity<>(categoryService.getSortedMenuCategories(), HttpStatus.OK);
    }

    @GetMapping("/menu/special-offer")
    public ResponseEntity<List<MenuDto>> getSpecialOffer() {

        return new ResponseEntity<>(menuService.getSpecialOfferMenu(), HttpStatus.OK);
    }

    @GetMapping("/menu-category/{categoryId}")
    public ResponseEntity<List<MenuDto>> getMenuOfCategory(@PathVariable("categoryId") Long categoryId) {

        LOGGER.info("--- Get menu-category by id: {}", categoryId);

        return new ResponseEntity<>(menuService.getMenuByCategoryId(categoryId), HttpStatus.OK);
    }

    @GetMapping("/menu-like/user/{userId}")
    public ResponseEntity<List<LikedMenuDto>> getUserLikedMenuId(@PathVariable("userId") Long userId) {

        LOGGER.info("--- Get liked menu for userId: {}", userId);
        return new ResponseEntity<>(menuService.getUserLikedMenu(userId), HttpStatus.OK);
    }

    @GetMapping("/favourite-menu/user/{userId}")
    public ResponseEntity<List<MenuDto>> getUserFavouriteMenu(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(menuService.getUserFavouriteMenu(userId), HttpStatus.OK);
    }

    @PostMapping("/add-to-favourite")
    public ResponseEntity<Void> addToFavourite(@RequestParam(value = "userId") Long userId,
                                               @RequestParam(value = "menuItemId") Long menuItemId) {
        LOGGER.info("--- addind to favourite: ");
        LOGGER.info("--- userId: {}", userId);
        LOGGER.info("--- menuItemId: {}", menuItemId);

        menuService.saveAsFavourite(menuItemId, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove-from-favourite")
    public ResponseEntity<Void> removeFromFavourite(@RequestParam(value = "userId") Long userId,
                                               @RequestParam(value = "menuItemId") Long menuItemId) {
        LOGGER.info("--- removing from favourite: ");
        LOGGER.info("--- userId: {}", userId);
        LOGGER.info("--- menuItemId: {}", menuItemId);

        menuService.deleteFromFavourite(menuItemId, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/menu/details/{menuId}")
    public ResponseEntity<DetailsDto> getDetailsMenu(@PathVariable("menuId") Long menuId) {

        return new ResponseEntity<>(detailsService.getDetailMenuItemById(menuId), HttpStatus.OK);
    }
}
