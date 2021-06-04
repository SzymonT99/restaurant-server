package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.OrderDetailsDto;
import com.restaurant.springboot.domain.dto.OrderItemDto;
import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.domain.entity.MenuOrders;
import com.restaurant.springboot.domain.entity.Order;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.mapper.OrderListMapper;
import com.restaurant.springboot.domain.model.Status;
import com.restaurant.springboot.domain.repository.MenuOrdersRepository;
import com.restaurant.springboot.domain.repository.MenuRepository;
import com.restaurant.springboot.domain.repository.OrderRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Service
public class OrderServiceImpl implements OrderService {

    private final MenuOrdersRepository menuOrdersRepository;
    private final OrderRepository orderRepository;
    private final OrderListMapper orderListMapper;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public OrderServiceImpl(MenuOrdersRepository menuOrdersRepository, OrderRepository orderRepository,
                            OrderListMapper orderListMapper, UserRepository userRepository, MenuRepository menuRepository,
                            JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.menuOrdersRepository = menuOrdersRepository;
        this.orderRepository = orderRepository;
        this.orderListMapper = orderListMapper;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
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
                orderItemDto.setPrice(quantity * orderItemDto.getPrice());  // sumowanie cen
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
    public Long createOrder(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (orderRepository.existsByPurchaserAndStatus(user, Status.SELECTION)) {
            Order lastOrder = orderRepository.findOneByStatusAndPurchaser(Status.SELECTION, user);
            return lastOrder.getOrderId();
        }
        else {
            Order emptyOrder = new Order();

            emptyOrder.setPurchaser(user);
            emptyOrder.setStatus(Status.SELECTION);

            orderRepository.save(emptyOrder);
            return emptyOrder.getOrderId();
        }
    }

    @Override
    public void addOrderItem(Long orderId, Long menuId) {

        Order order = orderRepository.findById(orderId).orElse(null);
        Menu selectedMenuItem = menuRepository.findById(menuId).orElse(null);

        List<MenuOrders> menuOrdersList = Objects.requireNonNull(order).getMenuOrders();
        MenuOrders menuOrders = new MenuOrders(selectedMenuItem, order);
        menuOrdersList.add(menuOrders);

        order.setMenuOrders(menuOrdersList);
        orderRepository.save(order);

    }

    @Override
    public void deleteOrderItem(Long orderId, Long menuId) {

        Order order = orderRepository.findById(orderId).orElse(null);
        Menu selectedMenuItem = menuRepository.findById(menuId).orElse(null);

        List<MenuOrders> menuOrdersList = menuOrdersRepository.findAllByMenuItemAndOrderItem(selectedMenuItem, order);

        Long id = menuOrdersList.get(0).getId();

        menuOrdersRepository.deleteByIdAndMenuItemAndOrderItem(id, selectedMenuItem, order);
    }

    @Override
    public Integer countAllOrderItems(Long orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);
        Integer numberOfOrderItems = null;
        if (order != null) {
            numberOfOrderItems = menuOrdersRepository.countAllByOrderItem(order);
        }

        return numberOfOrderItems;
    }

    @Override
    public void sendInvoiceToEmail(OrderDetailsDto details, Long userId, Long orderId) {

        User user = userRepository.findById(userId).orElse(null);
        Order order = orderRepository.findById(orderId).orElse(null);

        if (user != null && order != null) {
            order.setStatus(Status.ACCEPTED);
            order.setDate(new Date());
            order.setDeliveryPlace(details.getTown());
            order.setStreet(details.getStreet());
            order.setPostalCode(details.getPostalCode());

            List<Menu> menuList = new ArrayList<>();

            for (MenuOrders item : order.getMenuOrders()) {
                Menu menuItem = item.getMenuItem();
                menuList.add(menuItem);
            }

            Float totalPrice = 0F;
            for (Menu menu : menuList) {
               totalPrice += menu.getPrice();
            }

            order.setTotalPrice(totalPrice);

            // wysłanie emaila
            Context context = new Context();
            context.setVariable("orderList", getOrderItemsByOrderId(orderId));
            context.setVariable("totalPrice", totalPrice + " zł");
            context.setVariable("payment", details.getPaymentMethod());
            context.setVariable("town", details.getTown());
            context.setVariable("street", details.getStreet());
            context.setVariable("postalCode", details.getPostalCode());
            String html = templateEngine.process("Order", context);

            MimeMessage mail = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mail, true);
                helper.setTo(user.getEmail());
                helper.setFrom("restaurant.toik2021@gmail.com");
                helper.setSubject("Restaurant App - Potwierdzenie zamówienia");
                helper.setSentDate(new Date());
                helper.setText(html, true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            javaMailSender.send(mail);
        }
    }


}
