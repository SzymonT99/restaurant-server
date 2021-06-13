package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{

    ConfirmationToken findByToken(String token);

}
