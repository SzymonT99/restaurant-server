package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.entity.*;
import com.restaurant.springboot.domain.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class TestController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    DetailsRepository detailRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    RestaurantTableRepository restaurantTableRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/menu")
    public ResponseEntity<List<Menu>> getMenu() {

        return new ResponseEntity<>(menuRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategory() {

        return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<List<Details>> getDetails() {

        return new ResponseEntity<>(detailRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getReviews() {

        return new ResponseEntity<>(reviewRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {

        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/tables")
    public ResponseEntity<List<RestaurantTable>> getTables() {

        return new ResponseEntity<>(restaurantTableRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {

        return new ResponseEntity<>(reservationRepository.findAll(), HttpStatus.OK);
    }
}
