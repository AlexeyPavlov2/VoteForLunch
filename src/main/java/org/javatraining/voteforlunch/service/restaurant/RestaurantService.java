package org.javatraining.voteforlunch.service.restaurant;

import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Dish;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.service.BaseService;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface RestaurantService extends BaseService<Restaurant> {
    Restaurant readByName(String name) throws NotFoundException;
    List<Restaurant> readAllSorted(Sort sort);
    List<Dish> getDishByRestaurantId(int id);

}
