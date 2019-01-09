package org.javatraining.voteforlunch.service.dish;

import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Dish;
import org.javatraining.voteforlunch.service.BaseService;

import java.util.List;

public interface DishService extends BaseService<Dish> {
    List<Dish> readByNameLike(String name);
    List<Dish> readPaginated(int page, int size);
    void deleteAllByRestaurantId(int restaurantId);
    public List<Dish> getDishByRestaurantId(int id);
}
