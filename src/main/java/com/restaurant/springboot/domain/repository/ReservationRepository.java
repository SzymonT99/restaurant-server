package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.Reservation;
import com.restaurant.springboot.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByClientOrderByEndBookingDesc(User user);
}
