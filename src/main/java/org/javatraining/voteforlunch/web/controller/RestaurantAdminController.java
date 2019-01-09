package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.dto.DishDto;
import org.javatraining.voteforlunch.dto.MenuItemAdminDto;
import org.javatraining.voteforlunch.dto.RestaurantDto;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Dish;
import org.javatraining.voteforlunch.model.MenuItem;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.service.dish.DishService;
import org.javatraining.voteforlunch.service.menu_item.MenuItemService;
import org.javatraining.voteforlunch.service.restaurant.RestaurantService;
import org.javatraining.voteforlunch.util.DateTimeUtil;
import org.javatraining.voteforlunch.util.entity.DishUtil;
import org.javatraining.voteforlunch.util.entity.MenuItemAdminUtil;
import org.javatraining.voteforlunch.util.entity.RestaurantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.javatraining.voteforlunch.util.entity.RestaurantUtil.createDtoListFromRestaurantList;

@RestController
@RequestMapping(value = RestaurantAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminController {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantAdminController.class);
    static final String REST_URL = "/admin/restaurants";
    private final RestaurantService restaurantService;
    private final DishService dishService;
    private final MenuItemService menuItemService;

    @Autowired
    private MenuItemAdminUtil menuItemAdminUtil;


    @Autowired
    public RestaurantAdminController(RestaurantService restaurantService, DishService dishService, MenuItemService menuItemService) {
        this.restaurantService = restaurantService;
        this.dishService = dishService;
        this.menuItemService = menuItemService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<RestaurantDto> getAll() {
        logger.info("Retrieve all restaurantsusers");
        return createDtoListFromRestaurantList(restaurantService.readAll());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.FOUND)
    public RestaurantDto read(@PathVariable int id) {
        logger.info("Get restaurant with if = {}", id);
        return RestaurantUtil.createDtoFrom(restaurantService.read(id));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public RestaurantDto update(@Valid @RequestBody RestaurantDto restaurantDto) {
        logger.info("Update restaurant {} with info {}", restaurantDto.getId(), restaurantDto);
        Restaurant restaurant = restaurantService.read(restaurantDto.getId());
        return RestaurantUtil.createDtoFrom(restaurantService.update(RestaurantUtil.updateRestaurantFromDto(restaurant, restaurantDto)));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<RestaurantDto> create(@Valid @RequestBody RestaurantDto restaurantDto, UriComponentsBuilder ucBuilder) {
        logger.info("Put new restaurant with info {}", restaurantDto);
        RestaurantDto created = RestaurantUtil.createDtoFrom(restaurantService.create(RestaurantUtil.createRestaurantFromDto(restaurantDto)));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAll() {
        logger.info("Delete all restaurants");
        restaurantService.deleteAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        logger.info("Delete restaurant with id = {}", id);
        restaurantService.delete(id);
    }

    @GetMapping("/{id}/dishes")
    @ResponseStatus(value = HttpStatus.OK)
    public List<DishDto> getDishes(@PathVariable("id") int id) {
        logger.info("Get dishes for with id = {}", id);
        return DishUtil.createDtoListFromDishList(restaurantService.read(id).getDishes());
    }

    @PostMapping("/{id}/dishes")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<DishDto> createDish(@Valid @RequestBody DishDto dishDto, @PathVariable("id") int id, UriComponentsBuilder ucBuilder) {
        logger.info("Put new dish with info {} fro restaurant with id = {}", dishDto, id);
        Dish createdDish = DishUtil.createDishFromDto(dishDto);
        createdDish.setRestaurant(restaurantService.read(id));
        createdDish = dishService.create(createdDish);
        DishDto created = DishUtil.createDtoFrom(createdDish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}dishes/")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}/dishes")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDishes(@PathVariable("id") int id) {
        logger.info("Delete all dishesGet dishes for restaurant with id = {}", id);
        dishService.deleteAllByRestaurantId(id);
    }

    @DeleteMapping("/{id}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteDishById(@PathVariable("id") int id, @PathVariable("dishId") int dishId) {
        logger.info("Delete dish with id = {} for restaurant with id = {}", dishId, id);
        Dish dish = dishService.read(dishId);
        int restaurantId = dish.getRestaurant().getId();
        if (restaurantId != id) {
            throw new NotFoundException("Dish with id = " + dishId + " for restaurant with id = " + id + " not found. Found for restaurant with id = " + restaurantId);
        }
        dishService.delete(dishId);
    }

    @GetMapping("/{id}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.OK)
    public DishDto getDishById(@PathVariable("id") int id, @PathVariable("dishId") int dishId) {
        logger.info("Get dish with id = {} for restaurant with id = {}", dishId, id);
        Dish dish = dishService.read(dishId);
        int restaurantId = dish.getRestaurant().getId();
        if (dish.getRestaurant().getId() != id) {
            throw new NotFoundException("Dish with id = " + dishId + " for restaurant with id = " + id + " not found. Found for restaurant with id = " + restaurantId);
        }
        return DishUtil.createDtoFrom(dish);
    }

    @PutMapping("/{id}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.OK)
    public DishDto updateDish(@Valid @RequestBody DishDto dishDto, @PathVariable("id") int id, @PathVariable("dishId") int dishId) {
        logger.info("Update dish {} with info {}", dishDto.getId(), dishDto);
        Dish dish = dishService.read(dishId);
        int restaurantId = dish.getRestaurant().getId();
        if (dishDto.getId() != dishId || restaurantId != id) {
            throw new NotFoundException("Dish with id = " + dishId + " for restaurant with id = " + id + " not found. Found for restaurant with id = " + restaurantId);
        }
        DishUtil.updateDishFromDto(dish, dishDto);
        Dish updatedDish = dishService.update(dish);
        return DishUtil.createDtoFrom(updatedDish);
    }

    //Working with menu

    @GetMapping("/{id}/menu/{date}/menuitems")
    @ResponseStatus(value = HttpStatus.OK)
    public List<MenuItemAdminDto> getMenu(@PathVariable("id") int id,
                                          @PathVariable("date") String date) {
        logger.info("Get menu");
        List<MenuItem> menuItems = menuItemService.readByDateAndRestaurant(id,
                DateTimeUtil.getParseDateString(date));
        return menuItemAdminUtil.createDtoListFromEntityList(menuItems);
    }

    @DeleteMapping("/{id}/menu/{date}/menuitems") //{menuitem_id}
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable("id") int id,
                                          @PathVariable("date") String date) {
        logger.info("Delete menu");
        menuItemService.deleteByDateAndRestaurant(id, DateTimeUtil.getParseDateString(date));
    }

    @GetMapping("/{id}/menu/{date}/menuitems/{menuItemsId}")
    @ResponseStatus(value = HttpStatus.OK)
    public MenuItemAdminDto getMenuItem(@PathVariable("id") int id,
                                          @PathVariable("date") String date, @PathVariable("menuItemsId") int menuItemsId) {
        logger.info("Get menu item");
        MenuItem menuItem = menuItemService.readByDateAndRestaurant(id, DateTimeUtil.getParseDateString(date))
                .stream().filter(el -> el.getId().equals(menuItemsId)).findFirst().orElseThrow(() -> new NotFoundException("Not dound menu items with id = " + menuItemsId));
        return menuItemAdminUtil.createDtoFromEntity(menuItem);

    }
    @PostMapping("/{id}/menu/{date}/menuitems/")
    @ResponseStatus(value = HttpStatus.OK)
    public MenuItemAdminDto addMenuItem(@PathVariable("id") int id,
                                        @PathVariable("date") String date, @Validated @RequestBody  MenuItemAdminDto dto) {
        logger.info("Create new menu item menu item");
        LocalDate dateFromPath = DateTimeUtil.getParseDateString(date);
        LocalDate dateFromDto = dto.getDate();
        if (!dateFromDto.equals(dateFromPath)) {
            throw new NotFoundException("Dates must be the same.");
        }
        MenuItemAdminDto menuItemAdminDto = new MenuItemAdminDto(0, dateFromDto, dto.getRestaurant_id(), dto.getDish_id(), dto.getPrice());
        MenuItem entityFromDto = menuItemAdminUtil.createEntityFromDto(menuItemAdminDto);
        return menuItemAdminUtil.createDtoFromEntity(menuItemService.create(entityFromDto));
    }

    @DeleteMapping("/{id}/menu/{date}/menuitems/{menuItemsId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMenuItem(@PathVariable("id") int id,
                                        @PathVariable("date") String date, @PathVariable("menuItemsId") int menuItemsId) {
        logger.info("Delete menu item");
        MenuItem menuItem = menuItemService.readByDateAndRestaurant(id, DateTimeUtil.getParseDateString(date))
                .stream().filter(el -> el.getId().equals(menuItemsId)).findFirst().orElseThrow(() -> new NotFoundException("Not found menu items with id = " + menuItemsId));
        menuItemService.delete(menuItem.getId());
    }




}
