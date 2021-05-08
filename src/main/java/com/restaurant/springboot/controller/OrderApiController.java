package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.dto.OrderItemDto;
import com.restaurant.springboot.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class OrderApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewApiController.class);
    private final OrderService orderService;

    public OrderApiController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDto>> getOrderItems(@PathVariable("orderId") Long orderId) {

        List<OrderItemDto> orderList = orderService.getOrderItemsByOrderId(orderId);

        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    @GetMapping("/order-create/{userId}")
    public ResponseEntity<Long> createEmptyOrder(@PathVariable("userId") Long userId) {

        LOGGER.info("--- order create: ");
        LOGGER.info("--- userId: {}", userId);

        Long orderId = orderService.createOrder(userId);

        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @PostMapping("/add-order-element")
    public ResponseEntity<Void> addToOrder(@RequestParam(value = "menuId") Long menuId,
                                           @RequestParam(value = "orderId") Long orderId) {

        LOGGER.info("--- add to order: ");
        LOGGER.info("--- menuId: {}", menuId);
        LOGGER.info("--- orderId: {}", orderId);

        orderService.addOrderItem(orderId, menuId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-order-element")
    public ResponseEntity<Void> removeFromOrder(@RequestParam(value = "menuId") Long menuId,
                                                @RequestParam(value = "orderId") Long orderId) {

        LOGGER.info("--- remove from order: ");
        LOGGER.info("--- menuId: {}", menuId);
        LOGGER.info("--- orderId: {}", orderId);

        orderService.deleteOrderItem(orderId, menuId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order/quantity/{orderId}")
    public ResponseEntity<Integer> getOrderQuantity(@PathVariable("orderId") Long orderId) {

        LOGGER.info("--- order quantity: ");
        LOGGER.info("--- orderId: {}", orderId);

        Integer orderQuantity = orderService.countAllOrderItems(orderId);

        return new ResponseEntity<>(orderQuantity, HttpStatus.OK);
    }

}

