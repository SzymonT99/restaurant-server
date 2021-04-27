package com.restaurant.springboot.service;

import com.restaurant.springboot.domain.entity.Details;

public interface DetailsService {

    Details getDetailMenuItemById(Long menuId);

    void updateAverageRateOfMenuItem(Long detailsId);

    void updateOrdersCounter(Long detailsId);

}
