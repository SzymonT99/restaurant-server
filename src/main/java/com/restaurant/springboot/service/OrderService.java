package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.OrderItemDto;

import java.util.List;

public interface OrderService {

    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    void createOrder(Long userId);

    void addOrderItem(Long orderId, Long menuId);

    void deleteOrderItem(Long orderId, Long menuId);

}
