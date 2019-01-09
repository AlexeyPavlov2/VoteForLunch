package org.javatraining.voteforlunch.service.menu_item;

import org.javatraining.voteforlunch.model.MenuItem;
import org.javatraining.voteforlunch.service.BaseService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface MenuItemService extends BaseService<MenuItem> {
    List<MenuItem> readByDateAndRestaurant(int restaurantId, LocalDate dateParam);
    void deleteByDateAndRestaurant(int restaurantId, LocalDate dateParam);
    List<MenuItem> readByDate(LocalDate dateParam);


}
