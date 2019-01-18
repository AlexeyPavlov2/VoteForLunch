package org.javatraining.voteforlunch.service.restaurant;

import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.service.BaseService;

public interface RestaurantService extends BaseService<Restaurant> {
    Restaurant readByName(String name) throws NotFoundException;
}
