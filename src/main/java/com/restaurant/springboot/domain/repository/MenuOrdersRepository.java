package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.domain.entity.MenuOrders;
import com.restaurant.springboot.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MenuOrdersRepository extends JpaRepository<MenuOrders, Long> {

    List<MenuOrders> findAllByOrderItem(Order order);

    Integer countAllByOrderItemAndMenuItem(Order order, Menu menu);

    @Transactional
    void deleteByMenuItemAndOrderItem(Menu menu, Order order);

}
