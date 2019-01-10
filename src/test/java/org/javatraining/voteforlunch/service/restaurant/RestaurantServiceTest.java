package org.javatraining.voteforlunch.service.restaurant;

import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.util.TestUtil;
import org.javatraining.voteforlunch.util.entity.RestaurantUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
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
        //TestUtil.assertMatch(service.read(RESTAURANT_1_ID), RESTAURANT_1, "dishes", "votes", "menuItems");
        System.out.println(service.read(RESTAURANT_1_ID));
    }

    @Test(expected = NotFoundException.class)
    public void readNotFound() {
        TestUtil.assertMatch(service.read(RESTAURANT_6_ID), RESTAURANT_6);
    }


    @Test
    public void readAll() {
        TestUtil.assertMatch(service.readAll(), RESTAURANTS, "dishes", "votes", "menuItems");
    }

    @Test
    public void readAllSorted() {

        TestUtil.assertMatch(service.readAllSorted(new Sort(Sort.Direction.ASC, "name")).stream().map(Restaurant::getName).collect(Collectors.toList()),
                RESTAURANTS.stream().sorted(Comparator.comparing(Restaurant::getName)).map(Restaurant::getName).collect(Collectors.toList()), "dishes", "menuItems", "votes");
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
        TestUtil.assertMatch(service.readAll(),
                RESTAURANTS.stream().filter(el -> el.getId() != RESTAURANT_2_ID).collect(Collectors.toList()), "dishes", "votes", "menuItems");
    }

    @Test
    public void deleteAll() {
        service.deleteAll();
        assertTrue(service.readAll().isEmpty());
    }

    @Test
    public void getDishes() {
        TestUtil.assertMatch(service.getDishByRestaurantId(RESTAURANT_1_ID),
                Arrays.asList(DISHES.get(1), DISHES.get(2),
                        DISHES.get(3), DISHES.get(4), DISHES.get(5)), "restaurant");



    }

}