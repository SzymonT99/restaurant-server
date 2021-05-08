package com.restaurant.springboot.domain.mapper;

import com.restaurant.springboot.domain.dto.DetailsDto;
import com.restaurant.springboot.domain.entity.Menu;
import org.springframework.stereotype.Component;
import java.util.Arrays;


@Component
public class DetailsMapper {

    public DetailsDto mapToDto(Menu menuItem) {

        DetailsDto detailsDto = new DetailsDto();

        detailsDto.setMenuId(menuItem.getMenuId());
        detailsDto.setItemName(menuItem.getItemName());
        detailsDto.setPrice(menuItem.getPrice());
        detailsDto.setRate(menuItem.getAverageRate());
        detailsDto.setIngredients(Arrays.asList(menuItem.getIngredients().split(", ")));
        detailsDto.setMenuItemImage(menuItem.getMenuItemImage());
        detailsDto.setCategoryName(menuItem.getCategory().getCategoryName());
        detailsDto.setDetails(menuItem.getDetails());


        return detailsDto;
    }

}
