package org.javatraining.voteforlunch.system_service;

import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.service.dish.DishService;
import org.javatraining.voteforlunch.service.restaurant.RestaurantService;
import org.javatraining.voteforlunch.service.role.RoleService;
import org.javatraining.voteforlunch.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class EntityInfo implements InfoContributor {
    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Integer> entityInfo = new LinkedHashMap<>();
        entityInfo.put("users:", userService.readAll().size());
        entityInfo.put("roles:", roleService.readAll().size());
        entityInfo.put("restaurants:", restaurantService.readAll().size());
        entityInfo.put("dishes:", dishService.readAll().size());
        entityInfo.put("votes:", voteRepository.findAll().size());

        builder.withDetail("entityInfo", entityInfo);
    }
}
