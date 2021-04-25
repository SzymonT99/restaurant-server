package com.restaurant.springboot.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    private boolean active;

    @NotNull
    @Column(name = "incorrect_login_counter")
    private Integer incorrectLoginCounter;

    @NotNull
    @Column(name = "account_verification")
    private boolean accountVerification;

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "menu_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    Set<Menu> likedMenu;

    @OneToMany(mappedBy = "purchaser", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Order> orders;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Reservation> reservations;

    public User() {
    }

    public User(@NotNull String login, @NotNull String password, @NotNull @Email String email, @NotNull String phoneNumber,
                @NotNull boolean active, @NotNull Integer incorrectLoginCounter, @NotNull boolean accountVerification) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.incorrectLoginCounter = incorrectLoginCounter;
        this.accountVerification = accountVerification;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getIncorrectLoginCounter() {
        return incorrectLoginCounter;
    }

    public void setIncorrectLoginCounter(Integer incorrectLoginCounter) {
        this.incorrectLoginCounter = incorrectLoginCounter;
    }

    public boolean isAccountVerification() {
        return accountVerification;
    }

    public void setAccountVerification(boolean accountVerification) {
        this.accountVerification = accountVerification;
    }

    public Set<Menu> getLikedMenu() {
        return likedMenu;
    }

    public void setLikedMenu(Set<Menu> likedMenu) {
        this.likedMenu = likedMenu;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
