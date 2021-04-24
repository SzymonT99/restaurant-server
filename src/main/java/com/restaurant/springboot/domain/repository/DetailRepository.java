package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<Details, Long> {
}