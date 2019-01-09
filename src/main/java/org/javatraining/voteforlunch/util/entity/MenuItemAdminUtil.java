package org.javatraining.voteforlunch.util.entity;

import lombok.NoArgsConstructor;
import org.javatraining.voteforlunch.dto.MenuItemAdminDto;
import org.javatraining.voteforlunch.model.Dish;
import org.javatraining.voteforlunch.model.MenuItem;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.service.dish.DishService;
import org.javatraining.voteforlunch.service.menu_item.MenuItemService;
import org.javatraining.voteforlunch.service.restaurant.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class MenuItemAdminUtil implements EntityUtil<MenuItem, MenuItemAdminDto> {
    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Override
    public MenuItemAdminDto createDtoFromEntity(MenuItem objectEntity) {
        return new MenuItemAdminDto(objectEntity.getId(), objectEntity.getDatei(),
                objectEntity.getRestaurant().getId(), objectEntity.getDish().getId(), objectEntity.getPrice());
    }

    @Override
    public MenuItem createEntityFromDto(MenuItemAdminDto menuItemAdminDto) {
        return new MenuItem(menuItemAdminDto.getId(), menuItemAdminDto.getDate(),
                restaurantService.read(menuItemAdminDto.getRestaurant_id()),
                dishService.read(menuItemAdminDto.getDish_id()), menuItemAdminDto.getPrice());
    }

    @Override
    public MenuItem updateEntityFromDto(MenuItem menuItem, MenuItemAdminDto menuItemAdminDto) {
        Restaurant restaurant = restaurantService.read(menuItemAdminDto.getRestaurant_id());
        Dish dish = dishService.read(menuItemAdminDto.getDish_id());
        menuItem.setRestaurant(restaurant);
        menuItem.setDish(dish);
        return menuItemService.update(menuItem);
    }

    @Override
    public MenuItem createNewEntityFromAnother(MenuItem menuItem) {
        return new MenuItem(
                menuItem.getId(), menuItem.getDatei(), menuItem.getRestaurant(),
                menuItem.getDish(), menuItem.getPrice());
    }


}
