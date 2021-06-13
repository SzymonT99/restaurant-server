package com.restaurant.springboot.domain.repository;

import com.restaurant.springboot.domain.entity.Details;
import com.restaurant.springboot.domain.entity.Review;
import com.restaurant.springboot.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsAllByDetailsAndReviewer(Details details, User user);

    Review findByReviewId(Long reviewId);

    @Transactional
    void deleteByReviewId(Long reviewId);

}
