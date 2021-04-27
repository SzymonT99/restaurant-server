package com.restaurant.springboot.domain.mapper;

import com.restaurant.springboot.domain.dto.LikedMenuDto;
import com.restaurant.springboot.domain.entity.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class MenuLikedMapper {

    public List<LikedMenuDto> mapToDto(List<Menu> menuList) {
        List<LikedMenuDto> likedMenuDtoList = new ArrayList<>();

        for(Menu menuItem : menuList) {
            LikedMenuDto likedMenuDto = new LikedMenuDto();

            likedMenuDto.setMenuId(menuItem.getMenuId());
            likedMenuDto.setMenuItemName(menuItem.getItemName());

            likedMenuDtoList.add(likedMenuDto);

        }

        return likedMenuDtoList;
    }
}
