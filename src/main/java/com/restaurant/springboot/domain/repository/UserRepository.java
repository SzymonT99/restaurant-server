package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);

    @Transactional
    void deleteByLogin(String login);

    boolean existsByPhoneNumber(String phoneNumber);

}
