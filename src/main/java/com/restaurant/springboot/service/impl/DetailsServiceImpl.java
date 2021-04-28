package com.restaurant.springboot.service.impl;

import com.restaurant.springboot.domain.entity.Details;
import com.restaurant.springboot.domain.entity.Menu;
import com.restaurant.springboot.domain.entity.Review;
import com.restaurant.springboot.domain.repository.DetailsRepository;
import com.restaurant.springboot.domain.repository.MenuRepository;
import com.restaurant.springboot.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsServiceImpl implements DetailsService {

    private final DetailsRepository detailRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public DetailsServiceImpl(DetailsRepository detailRepository, MenuRepository menuRepository) {
        this.detailRepository = detailRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Details getDetailMenuItemById(Long menuId) {
        Details details = detailRepository.findById(menuId).orElse(null);
        return details;
    }

    @Override
    public void updateAverageRateOfMenuItem(Long detailsId) {
        Details updatedMenuDetails = detailRepository.findById(detailsId).orElse(null);

        Double newAverageRate = 0.0;

        for (Review review : updatedMenuDetails.getReviews()) {
            newAverageRate += review.getRate();
        }

        newAverageRate = newAverageRate / updatedMenuDetails.getReviews().size();

        System.out.println(newAverageRate);

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
