package org.javatraining.voteforlunch.util.entity;

import org.javatraining.voteforlunch.dto.RestaurantDto;
import org.javatraining.voteforlunch.model.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    public static RestaurantDto createDtoFrom(Restaurant restaurant) {
        return new RestaurantDto(restaurant.getId(), restaurant.getName(),
                restaurant.getDescription(), restaurant.getContact(), restaurant.getSite(),
                restaurant.getEmail(), restaurant.getPhones());
    }

    public static List<RestaurantDto> createDtoListFromRestaurantList(List<Restaurant> restaurantList) {
        return restaurantList.stream().map(el -> new RestaurantDto(el.getId(), el.getName(),
                el.getDescription(), el.getContact(), el.getSite(),
                el.getEmail(), el.getPhones())).collect(Collectors.toList());
    }

    public static Restaurant createRestaurantFromDto(RestaurantDto restaurant) {
        return new Restaurant(restaurant.getId(), restaurant.getName(),
                restaurant.getDescription(), restaurant.getContact(), restaurant.getSite(),
                restaurant.getEmail(), restaurant.getPhones(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList() );
    }

    public static Restaurant updateRestaurantFromDto(Restaurant restaurant, RestaurantDto restaurantDto) {
        restaurant.setName(restaurantDto.getName());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setContact(restaurantDto.getContact());
        restaurant.setSite(restaurantDto.getSite());
        restaurant.setEmail(restaurantDto.getEmail().toLowerCase());
        restaurant.setPhones(restaurantDto.getPhones());
        return restaurant;
    }

    public static Restaurant createNewFromAnother(Restaurant restaurant) {
        return new Restaurant(
                restaurant.getId(), restaurant.getName(), restaurant.getDescription(),
                restaurant.getContact(), restaurant.getSite(), restaurant.getEmail(),
                restaurant.getPhones(), new ArrayList(restaurant.getDishes()), new ArrayList(restaurant.getDishes()), new ArrayList(restaurant.getDishes()) ) ;
    }

}
