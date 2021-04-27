package com.restaurant.springboot.controller;

import com.restaurant.springboot.domain.dto.ReviewDto;
import com.restaurant.springboot.domain.entity.Review;
import com.restaurant.springboot.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class ReviewApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewApiController.class);

    private final ReviewService reviewService;

    public ReviewApiController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add-review")
    public ResponseEntity<Boolean> addReview(@RequestBody ReviewDto reviewDto) {

        LOGGER.info("--- add-review");
        LOGGER.info("--- userId: {}", reviewDto.getUserId());
        LOGGER.info("--- menuId: {}", reviewDto.getDetailsId());
        LOGGER.info("--- comment: {}", reviewDto.getComment());
        LOGGER.info("--- rate: {}", reviewDto.getRate());

        boolean state = reviewService.saveReviewOfMenu(reviewDto);

        return new ResponseEntity<>(state, HttpStatus.OK);
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<Review> deleteQuestion(@PathVariable("reviewId") Long reviewId) {

        Review deletedReview = reviewService.getReviewById(reviewId);
        reviewService.deleteReviewById(reviewId);

        return new ResponseEntity<>(deletedReview, HttpStatus.OK);
    }
}
