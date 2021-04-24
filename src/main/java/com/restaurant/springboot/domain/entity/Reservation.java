package com.restaurant.springboot.domain.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_nr")
    private Long reservationNumber;

    @DateTimeFormat
    @Column(name = "booking_date")
    private Date bookingdate;

    public Reservation() {
    }

    public Reservation(Date bookingdate) {
        this.bookingdate = bookingdate;
    }

    public Date getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(Date bookingdate) {
        this.bookingdate = bookingdate;
    }
}
