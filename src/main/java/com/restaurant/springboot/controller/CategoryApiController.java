package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.entity.Category;
import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class CategoryApiController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {

        return new ResponseEntity<>(categoryService.getSortedMenuCategories(), HttpStatus.OK);
    }
}
