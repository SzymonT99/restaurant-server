package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByCategoryId(Long categoryId);

    List<Menu> findAllBySpecialOffer(boolean special);
}
