package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.dto.DetailsDto;
import com.restaurant.springboot.domain.entity.Details;
import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.domain.entity.Review;
import com.restaurant.springboot.domain.mapper.DetailsMapper;
import com.restaurant.springboot.domain.repository.DetailsRepository;
import com.restaurant.springboot.domain.repository.MenuRepository;
import com.restaurant.springboot.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailsServiceImpl implements DetailsService {

    private final DetailsRepository detailRepository;
    private final MenuRepository menuRepository;
    private final DetailsMapper detailsMapper;

    @Autowired
    public DetailsServiceImpl(DetailsRepository detailRepository, MenuRepository menuRepository,
                              DetailsMapper detailsMapper) {
        this.detailRepository = detailRepository;
        this.menuRepository = menuRepository;
        this.detailsMapper = detailsMapper;
    }

    @Override
    public DetailsDto getDetailMenuItemById(Long menuId) {
        Menu menuItem = menuRepository.findById(menuId).orElse(null);
        List<Review> reviews = menuItem.getDetails().getReviews();

        List<Review> sortedReviews = new ArrayList<>();

        for (int i = 0; i < reviews.size(); i++) {
            for (int j = 0; j < reviews.size(); j++) {
                if (reviews.get(i).getReviewId() > reviews.get(j).getReviewId()) {
                    reviews.set(i, reviews.get(j));
                }
            }
        }

        menuItem.getDetails().setReviews(reviews);

        return detailsMapper.mapToDto(menuItem);
    }

    @Override
    public void updateAverageRateOfMenuItem(Long detailsId) {
        Details updatedMenuDetails = detailRepository.findById(detailsId).orElse(null);

        Double newAverageRate = 0.0;

        for (Review review : updatedMenuDetails.getReviews()) {
            newAverageRate += review.getRate();
        }

        newAverageRate = newAverageRate / updatedMenuDetails.getReviews().size();

        Menu menu = menuRepository.findById(detailsId).orElse(null);
        menu.setAverageRate(newAverageRate);
        menuRepository.save(menu);

    }

    @Override
    public void updateOrdersCounter(Long detailsId) {
        Details updatedMenuDetails = detailRepository.findById(detailsId).orElse(null);
        updatedMenuDetails.setOrdersNumber(updatedMenuDetails.getOrdersNumber() + 1);
        detailRepository.save(updatedMenuDetails);
    }
}
