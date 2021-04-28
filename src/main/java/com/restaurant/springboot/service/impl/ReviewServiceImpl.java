package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.ReviewDto;
import com.restaurant.springboot.domain.entity.Details;
import com.restaurant.springboot.domain.entity.Review;
import com.restaurant.springboot.domain.entity.User;
import com.restaurant.springboot.domain.repository.DetailsRepository;
import com.restaurant.springboot.domain.repository.ReviewRepository;
import com.restaurant.springboot.domain.repository.UserRepository;
import com.restaurant.springboot.service.DetailsService;
import com.restaurant.springboot.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final DetailsRepository detailRepository;
    private final DetailsService detailsService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository,
                             DetailsRepository detailRepository, DetailsService detailsService) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.detailRepository = detailRepository;
        this.detailsService = detailsService;
    }

    @Override
    public Boolean saveReviewOfMenu(ReviewDto reviewDto) {

        User user = userRepository.findById(reviewDto.getUserId()).orElse(null);
        Details details = detailRepository.findById(reviewDto.getDetailsId()).orElse(null);

        boolean checkingOfUserComment = reviewRepository.existsAllByDetailsAndReviewer(details, user);

        if (!checkingOfUserComment) {
            Review review = new Review(reviewDto.getComment(), reviewDto.getRate(), details, user);
            reviewRepository.save(review);
            detailsService.updateAverageRateOfMenuItem(reviewDto.getDetailsId());   // aktualizacja średniej ocen
            detailsService.updateOrdersCounter(reviewDto.getDetailsId());           // aktualizacja liczby zamówień
            return true;
        }
        return false;
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findByReviewId(reviewId);
    }

    @Override
    public void deleteReviewById(Long reviewId) {
        reviewRepository.deleteByReviewId(reviewId);
    }
}
