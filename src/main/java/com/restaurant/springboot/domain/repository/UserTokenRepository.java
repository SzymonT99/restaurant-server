package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    boolean existsByUser(User user);

    UserToken findByUser(User user);

    Optional<UserToken> findByToken(String token);

}

