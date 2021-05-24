package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.dto.ReservationDto;
import com.restaurant.springboot.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/restaurant")
public class ReservationApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewApiController.class);
    private final ReservationService reservationService;

    @Autowired
    public ReservationApiController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/create-reservation")
    public ResponseEntity<Void> bookTable(@RequestBody ReservationDto reservationDto) {

        LOGGER.info("--- add-reservation");
        LOGGER.info("--- userId: {}", reservationDto.getUserId());
        LOGGER.info("--- startBooking date: {}", reservationDto.getStartBooking());
        LOGGER.info("--- time: {}", reservationDto.getBookingTime());
        LOGGER.info("--- number of table seats: {}", reservationDto.getNumberOfTableSeats());

        boolean status = reservationService.createReservation(reservationDto);

        LOGGER.info("--- status: {}", status ? "booked" : "reservation cannot be made");

        return status
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/delete-last-reservation/{userId}")
    public ResponseEntity<Void> removeLastBooking(@PathVariable("userId") Long userId) {

        LOGGER.info("--- delete last reservation");
        LOGGER.info("--- userId: {}", userId);

        boolean status = reservationService.deleteLastUserReservation(userId);

        return status
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
