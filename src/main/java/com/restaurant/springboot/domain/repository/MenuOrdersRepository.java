package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.MenuOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOrdersRepository extends JpaRepository<MenuOrders, Long> {
}
