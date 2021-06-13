package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_nr")
    private Long reservationNumber;

    @DateTimeFormat
    @NotNull
    @Column(name = "start_booking")
    private Date startBooking;

    @DateTimeFormat
    @NotNull
    @Column(name = "end_booking")
    private Date endBooking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    @JsonBackReference
    private RestaurantTable table;

    @Transient
    private Integer tableId;

    public Reservation() {
    }

    public Reservation(@NotNull Date startBooking, @NotNull Date endBooking, User client, RestaurantTable table) {
        this.startBooking = startBooking;
        this.endBooking = endBooking;
        this.client = client;
        this.table = table;
    }

    public Integer getTableId() {
        return getTable().getTableId();
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Long getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Long reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public Date getStartBooking() {
        return startBooking;
    }

    public void setStartBooking(Date startBooking) {
        this.startBooking = startBooking;
    }

    public Date getEndBooking() {
        return endBooking;
    }

    public void setEndBooking(Date endBooking) {
        this.endBooking = endBooking;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

}
