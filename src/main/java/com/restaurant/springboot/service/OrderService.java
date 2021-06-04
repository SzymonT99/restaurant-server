package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.OrderDetailsDto;
import com.restaurant.springboot.domain.dto.OrderItemDto;

import java.util.List;

public interface OrderService {

    List<OrderItemDto> getOrderItemsByOrderId(Long orderId);

    Long createOrder(Long userId);

    void addOrderItem(Long orderId, Long menuId);

    void deleteOrderItem(Long orderId, Long menuId);

    Integer countAllOrderItems (Long orderId);

    void sendInvoiceToEmail(OrderDetailsDto details, Long userId, Long orderId);

}
