package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.ReviewDto;
import com.restaurant.springboot.domain.entity.Review;

public interface ReviewService {

    Boolean saveReviewOfMenu(ReviewDto reviewDto);

    Review getReviewById(Long reviewId);

    void deleteReviewById(Long reviewId);
}
