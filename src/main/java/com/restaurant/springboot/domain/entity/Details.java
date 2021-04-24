package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "details")
public class Details {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "details_id")
    private Long detailsId;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "detailed_image")
    private String detailedImage;

    @NotNull
    @Column(name = "orders_number")
    private Integer ordersNumber;

    @NotNull
    @Column(name = "average_rate")
    private Integer averageRate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menu;

    @OneToMany(mappedBy = "details", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Review> reviews;

    public Details() {
    }

    public Details(@NotNull String description, @NotNull String detailedImage,
                   @NotNull Integer ordersNumber, @NotNull Integer averageRate, Menu menu) {
        this.description = description;
        this.detailedImage = detailedImage;
        this.ordersNumber = ordersNumber;
        this.averageRate = averageRate;
        this.menu = menu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedImage() {
        return detailedImage;
    }

    public void setDetailedImage(String detailedImage) {
        this.detailedImage = detailedImage;
    }

    public Integer getOrdersNumber() {
        return ordersNumber;
    }

    public void setOrdersNumber(Integer ordersNumber) {
        this.ordersNumber = ordersNumber;
    }

    public Integer getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Integer averageRate) {
        this.averageRate = averageRate;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
}
