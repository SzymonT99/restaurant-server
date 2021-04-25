package com.restaurant.springboot.domain.mapper;

import com.restaurant.springboot.domain.dto.MenuDto;
import com.restaurant.springboot.domain.entity.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MenuListMapper {

    public List<MenuDto> mapToDto(List<Menu> menuList) {
        List<MenuDto> menuDtoList = new ArrayList<>();

        for(Menu menuItem : menuList) {
            MenuDto menuDto = new MenuDto();

            menuDto.setMenuId(menuItem.getMenuId());
            menuDto.setItemName(menuItem.getItemName());
            menuDto.setPrice(menuItem.getPrice());
            menuDto.setRate(menuItem.getAverageRate());
            menuDto.setIngridients(Arrays.asList(menuItem.getIngridients().split(", ")));
            menuDto.setMenuItemImage(menuItem.getMenuItemImage());
            menuDto.setDetailsId(menuItem.getDetails().getDetailsId());
            menuDto.setCategoryName(menuItem.getCategory().getCategoryName());

            menuDtoList.add(menuDto);

        }

        return menuDtoList;
    }

}
