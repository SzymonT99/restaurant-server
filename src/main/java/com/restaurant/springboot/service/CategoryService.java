package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getSortedMenuCategories();

}
