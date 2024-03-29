package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotNull
    @Column(name = "category_name")
    private String categoryName;

    @NotNull
    @Column(name = "category_image")
    private String categoryImage;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Menu> menuItemsOfCategory;

    public Category() {
    }

    public Category(@NotNull String categoryName, @NotNull String categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Set<Menu> getMenuItemsOfCategory() {
        return menuItemsOfCategory;
    }

    public void setMenuItemsOfCategory(Set<Menu> menuItemsOfCategory) {
        this.menuItemsOfCategory = menuItemsOfCategory;
    }
}
