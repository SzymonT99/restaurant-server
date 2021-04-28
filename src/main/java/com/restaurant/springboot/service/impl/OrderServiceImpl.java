package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.OrderItemDto;
import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.domain.entity.MenuOrders;
import com.restaurant.springboot.domain.entity.Order;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.mapper.OrderListMapper;
import com.restaurant.springboot.domain.repository.MenuOrdersRepository;
import com.restaurant.springboot.domain.repository.MenuRepository;
import com.restaurant.springboot.domain.repository.OrderRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    private final MenuOrdersRepository menuOrdersRepository;
    private final OrderRepository orderRepository;
    private final OrderListMapper orderListMapper;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public OrderServiceImpl(MenuOrdersRepository menuOrdersRepository, OrderRepository orderRepository,
                            OrderListMapper orderListMapper, UserRepository userRepository, MenuRepository menuRepository) {
        this.menuOrdersRepository = menuOrdersRepository;
        this.orderRepository = orderRepository;
        this.orderListMapper = orderListMapper;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public List<OrderItemDto> getOrderItemsByOrderId(Long orderId) {

        List<OrderItemDto> orderList;

        Order order = orderRepository.findById(orderId).orElse(null);
        List<MenuOrders> menuOrdersList = menuOrdersRepository.findAllByOrderItem(order);
        List<Menu> menuList = new ArrayList<>();

        for (MenuOrders menuOrders : menuOrdersList) {
            Menu menuItem = menuOrders.getMenuItem();
            menuList.add(menuItem);
        }

        orderList = orderListMapper.mapToDto(menuList);


        // sprawdzanie występowania powtórzonych elementów w zamówieniu
        for (int i = 0; i < orderList.size(); i++) {
            Menu menu = menuList.get(i);
            int quantity = menuOrdersRepository.countAllByOrderItemAndMenuItem(order, menu);
            if (quantity > 1) {
                OrderItemDto orderItemDto = orderList.get(i);
                orderItemDto.setQuantity(quantity);
                orderList.set(i, orderItemDto);
            }
        }

        List<OrderItemDto> filteredOrderList = new ArrayList<>();

        for (OrderItemDto orderItemDto : orderList) {
            if (!filteredOrderList.contains(orderItemDto)) {
                filteredOrderList.add(orderItemDto);
            }
        }

        return filteredOrderList;
    }

    @Override
    public void createOrder(Long userId) {
        Order emptyOrder = new Order();

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String date = String.valueOf(new Date());

        User user = userRepository.findById(userId).orElse(null);
        emptyOrder.setPurchaser(user);
        emptyOrder.setDate(new Date());

        orderRepository.save(emptyOrder);
    }

    @Override
    public void addOrderItem(Long orderId, Long menuId) {

        Order order = orderRepository.findById(orderId).orElse(null);
        Menu selectedMenuItem = menuRepository.findById(menuId).orElse(null);

        List<MenuOrders> menuOrdersList = order.getMenuOrders();
        MenuOrders menuOrders = new MenuOrders(selectedMenuItem, order);
        menuOrdersList.add(menuOrders);

        order.setMenuOrders(menuOrdersList);
        orderRepository.save(order);

    }

    @Override
    public void deleteOrderItem(Long orderId, Long menuId) {

        Order order = orderRepository.findById(orderId).orElse(null);
        Menu selectedMenuItem = menuRepository.findById(menuId).orElse(null);

//        List<MenuOrders> menuOrdersList = order.getMenuOrders();
//        MenuOrders menuOrders = new MenuOrders(selectedMenuItem, order);
//        menuOrdersList.remove(menuOrders);
//
//        order.setMenuOrders(menuOrdersList);

        menuOrdersRepository.deleteByMenuItemAndOrderItem(selectedMenuItem, order);
    }

}
