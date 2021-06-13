package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "details")
public class Details {

    @Id
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private Menu menu;

    @OneToMany(mappedBy = "details", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;

    public Details() {
    }

    public Details(@NotNull String description, @NotNull String detailedImage, @NotNull Integer ordersNumber) {
        this.description = description;
        this.detailedImage = detailedImage;
        this.ordersNumber = ordersNumber;
    }

    public Long getDetailsId() {
        return detailsId;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
