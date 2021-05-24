package com.restaurant.springboot.domain.dto;

public class ReservationDto {

    private String startBooking;
    private Integer bookingTime;
    private Long userId;
    private Integer numberOfTableSeats;

    public ReservationDto() {
    }

    public ReservationDto(String startBooking, Integer bookingTime, Long userId, Integer numberOfTableSeats) {
        this.startBooking = startBooking;
        this.bookingTime = bookingTime;
        this.userId = userId;
        this.numberOfTableSeats = numberOfTableSeats;
    }

    public String getStartBooking() {
        return startBooking;
    }

    public void setStartBooking(String startBooking) {
        this.startBooking = startBooking;
    }

    public Integer getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Integer bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getNumberOfTableSeats() {
        return numberOfTableSeats;
    }

    public void setNumberOfTableSeats(Integer numberOfTableSeats) {
        this.numberOfTableSeats = numberOfTableSeats;
    }
}
