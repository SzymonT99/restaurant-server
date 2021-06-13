package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Integer> {

    List<RestaurantTable> findAllBySeatsNumber(Integer seatsNumber);
}
