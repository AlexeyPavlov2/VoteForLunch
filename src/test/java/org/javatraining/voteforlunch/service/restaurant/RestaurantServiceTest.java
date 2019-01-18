package org.javatraining.voteforlunch.service.restaurant;

import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Dish;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.util.TestUtil;
import org.javatraining.voteforlunch.util.entity.RestaurantUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.javatraining.voteforlunch.util.DishTestData.DISHES;
import static org.javatraining.voteforlunch.util.RestaurantTestData.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RestaurantServiceTest {
    @Autowired
    private RestaurantService service;

    @Autowired
    CacheManager cacheManager;

    @Before
    public void beforeEach() throws Exception {
        cacheManager.getCache("restaurants").clear();
        cacheManager.getCache("dishes").clear();
        cacheManager.getCache("menuItems").clear();


    }


    @Test
    public void create() {
        TestUtil.assertMatch(service.create(RestaurantUtil.createNewFromAnother(RESTAURANT_6)),
                RESTAURANT_6, "dishes");
    }

    @Test
    public void read() {
        Restaurant restaurant = service.read(RESTAURANT_1_ID);
        TestUtil.assertMatch(service.read(RESTAURANT_1_ID), RESTAURANT_1, "dishes", "votes", "menuItems");
    }

    @Test(expected = NotFoundException.class)
    public void readNotFound() {
        TestUtil.assertMatch(service.read(RESTAURANT_6_ID), RESTAURANT_6);
    }


    @Test
    public void readAll() {
        List<Restaurant> list = service.readAll();
        list.forEach(el -> {
            System.out.println(el.getId() + " : " + el.getName());
            el.getDishes().forEach(el1 -> {
                System.out.println(el1.getName());
            });
        });


        //TestUtil.assertMatch(service.readAll(), RESTAURANTS, "dishes", "votes", "menuItems");
    }


    @Test
    public void readByName() {
        TestUtil.assertMatch(service.readByName(RESTAURANT_2.getName()), RESTAURANT_2, "dishes", "votes", "menuItems");
    }

    @Test(expected = NotFoundException.class)
    public void readByNameNotFound() {
        TestUtil.assertMatch(service.readByName(RESTAURANT_6.getName()), RESTAURANT_6, "dishes");
    }

    @Test
    public void update() {
        String name = RESTAURANT_2.getName() + "UPDATED";
        Restaurant restaurant = service.read(RESTAURANT_2_ID);
        restaurant.setName(name);
        assertEquals(service.update(restaurant).getName(), name);
    }

    @Test
    public void delete() {
        service.delete(RESTAURANT_2_ID);
        List<Restaurant> actual = service.readAll().stream().sorted(Comparator.comparing(Restaurant::getId)).collect(Collectors.toList());
        List<Restaurant> expected = RESTAURANTS.stream().filter(el -> el.getId() != RESTAURANT_2_ID).sorted(Comparator.comparing(Restaurant::getId)).collect(Collectors.toList());
        TestUtil.assertMatch(RestaurantUtil.createDtoListFromRestaurantList(actual), RestaurantUtil.createDtoListFromRestaurantList(expected));
    }

    @Test
    public void deleteAll() {
        service.deleteAll();
        assertTrue(service.readAll().isEmpty());
    }


    @Test
    public void getDishes() {
        TestUtil.assertMatch(service.read(RESTAURANT_1_ID).getDishes().stream().sorted(Comparator.comparing(Dish::getId)).collect(Collectors.toList()),
                Arrays.asList(DISHES.get(1), DISHES.get(2),
                        DISHES.get(3), DISHES.get(4), DISHES.get(5)).stream().sorted(Comparator.comparing(Dish::getId)).collect(Collectors.toList()), "restaurant");
   }


}