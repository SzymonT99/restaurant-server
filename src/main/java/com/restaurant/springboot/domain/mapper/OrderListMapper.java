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
            orderItemDto.setIngridients(Arrays.asList(menuItem.getIngridients().split(", ")));
            orderItemDto.setPrice(menuItem.getPrice());
            orderItemDto.setQuantity(1);
            orderItemDto.setMenuItemImage(menuItem.getMenuItemImage());
            orderItemDto.setRate(menuItem.getAverageRate());

            orderedItems.add(orderItemDto);
        }

        return orderedItems;
    }

}
