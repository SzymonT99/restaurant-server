package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.entity.Category;
import com.restaurant.springboot.domain.repository.CategoryRepository;
import com.restaurant.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getSortedMenuCategories() {
        return categoryRepository.findByOrderByCategoryNameAsc();
    }
}
