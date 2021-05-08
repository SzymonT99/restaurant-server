package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.Order;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.model.Status;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByPurchaserAndStatus(User user, Status status);

    Order findOneByStatusAndPurchaser(Status status, User user);

}
