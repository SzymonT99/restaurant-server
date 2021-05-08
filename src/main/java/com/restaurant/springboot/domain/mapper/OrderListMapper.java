package com.restaurant.springboot.domain.mapper;

import com.restaurant.springboot.domain.dto.OrderItemDto;
import com.restaurant.springboot.domain.entity.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class OrderListMapper {

    public List<OrderItemDto> mapToDto(List<Menu> menuList) {
        List<OrderItemDto> orderedItems = new ArrayList<>();

        for(Menu menuItem : menuList) {
            OrderItemDto orderItemDto = new OrderItemDto();

            orderItemDto.setItemName(menuItem.getItemName());
            orderItemDto.setIngredients(Arrays.asList(menuItem.getIngredients().split(", ")));
            orderItemDto.setPrice(menuItem.getPrice());
            orderItemDto.setQuantity(1);
            orderItemDto.setMenuItemImage(menuItem.getMenuItemImage());
            orderItemDto.setRate(menuItem.getAverageRate());
            orderItemDto.setDetailsId(menuItem.getDetails().getDetailsId());

            orderedItems.add(orderItemDto);
        }

        return orderedItems;
    }

}
