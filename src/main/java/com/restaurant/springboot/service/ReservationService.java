package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.ReservationDto;
import com.restaurant.springboot.domain.entity.RestaurantTable;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    boolean createReservation(ReservationDto reservationDto);

    boolean checkAvailabilityOfDate(Date startBooking, Integer time);

    List<RestaurantTable> getTables(Integer seats);

    boolean deleteLastUserReservation(Long userId);
}
