package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.dto.DetailsDto;

public interface DetailsService {

    DetailsDto getDetailMenuItemById(Long menuId);

    void updateAverageRateOfMenuItem(Long detailsId);

    void updateOrdersCounter(Long detailsId);

}
