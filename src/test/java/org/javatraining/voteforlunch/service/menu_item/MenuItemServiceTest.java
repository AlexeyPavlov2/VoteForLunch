package org.javatraining.voteforlunch.service.menu_item;

import org.javatraining.voteforlunch.TestUtil;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Dish;
import org.javatraining.voteforlunch.model.MenuItem;
import org.javatraining.voteforlunch.service.dish.DishService;
import org.javatraining.voteforlunch.service.restaurant.RestaurantService;
import org.javatraining.voteforlunch.util.entity.MenuItemAdminUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.javatraining.voteforlunch.util.DishTestData.DISHES;
import static org.javatraining.voteforlunch.util.MenuItemTestData.*;
import static org.javatraining.voteforlunch.util.RestaurantTestData.RESTAURANT_1_ID;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MenuItemServiceTest {
    @Autowired
    private RestaurantService service;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private DishService dishService;

    @Autowired
    private MenuItemAdminUtil menuItemAdminUtil;

    @Autowired
    CacheManager cacheManager;

    @Before
    public void beforeEach() throws Exception {
        cacheManager.getCache("restaurants").clear();
        cacheManager.getCache("dishes").clear();
        cacheManager.getCache("menuItems").clear();
    }



    @Test
    public void readByDateAndRestaurant() {
        TestUtil.assertMatch(menuItemService.readByDateAndRestaurant(1, DATE_1),
                Arrays.asList(MENU_ITEM_1_RESTAURANT_1_DATE_1, MENU_ITEM_2_RESTAURANT_1_DATE_1, MENU_ITEM_3_RESTAURANT_1_DATE_1), "dish", "restaurant");


    }

    @Test
    public void deleteByDateAndRestaurant() {
        menuItemService.deleteByDateAndRestaurant(RESTAURANT_1_ID, DATE_1);
        assertTrue(menuItemService.readByDateAndRestaurant(RESTAURANT_1_ID, DATE_1).isEmpty());
    }

    @Test
    public void readByDate() {
        TestUtil.assertMatch(menuItemService.readByDate(DATE_1), MENU.stream().filter(el -> el.getDatei().equals(DATE_1)).collect(Collectors.toList()), "dish", "restaurant");
    }

    @Test
    public void create() {
        MenuItem newMenuItem = menuItemAdminUtil.createNewEntityFromAnother(NEW_MENU_ID_ITEM_23_RESTAURANT_3_DATE_3);
        TestUtil.assertMatch(menuItemService.create(newMenuItem), NEW_MENU_ID_ITEM_23_RESTAURANT_3_DATE_3, "dish", "restaurant");

    }

    @Test
    public void read() {
        TestUtil.assertMatch(menuItemService.read(MENU_ITEM_2_RESTAURANT_1_DATE_1.getId()), MENU_ITEM_2_RESTAURANT_1_DATE_1, "dish", "restaurant");
    }

    @Test
    public void readAll() {
        //TestUtil.assertMatch(menuItemService.readAll(), MENU, "dish", "restaurant");
        List<String> actual = menuItemService.readAll().stream().map(el -> new String(el.getId() + " " + el.getDatei() + " " + el.getDish().getId() + " " + el.getRestaurant().getId())).collect(Collectors.toList());
        List<String> expected = MENU.stream().map(el -> new String(el.getId() + " " + el.getDatei() + " " + el.getDish().getId() + " " + el.getRestaurant().getId())).collect(Collectors.toList());
        TestUtil.assertMatch(actual, expected);

    }

    @Test
    public void update() {
        int dishId = 5;
        int id = MENU_ITEM_1_RESTAURANT_1_DATE_2_ID;
        Dish updatedDish = DISHES.get(dishId);
        MenuItem menuItem = menuItemService.read(MENU_ITEM_1_RESTAURANT_1_DATE_2_ID);
        menuItem.setDish(updatedDish);
        TestUtil.assertMatch(menuItemService.read(MENU_ITEM_1_RESTAURANT_1_DATE_2_ID).getDish(), updatedDish, "restaurant");

    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        menuItemService.delete(MENU_ITEM_1_RESTAURANT_1_DATE_2_ID);
        menuItemService.read(MENU_ITEM_1_RESTAURANT_1_DATE_2_ID);
    }

    @Test
    public void deleteAll() {
        menuItemService.deleteAll();
        assertTrue(menuItemService.readAll().isEmpty());
    }
}