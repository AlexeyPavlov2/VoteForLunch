package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.dto.DishDtoForUser;
import org.javatraining.voteforlunch.dto.MenuDtoForUser;
import org.javatraining.voteforlunch.dto.ResultObject;
import org.javatraining.voteforlunch.dto.VoteDto;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.MenuItem;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.model.Vote;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.service.menu_item.MenuItemService;
import org.javatraining.voteforlunch.util.DateTimeUtil;
import org.javatraining.voteforlunch.util.entity.RestaurantUtil;
import org.javatraining.voteforlunch.util.entity.VoteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = PublicController.REST_URL)
@ResponseStatus(value = HttpStatus.FOUND)
public class PublicController {
    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);
    static final String REST_URL = "/public";
    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteUtil voteUtil;

    @GetMapping(value = "/menu/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<MenuDtoForUser> getMenu(@PathVariable("date") String date) {
        logger.info("Get the menu for the specified date");
        // Поскольку ObjectMapper у меня толком не заработал...
        List<MenuItem> menuItems = menuItemService.readByDate(DateTimeUtil.getParseDateString(date));
        if (menuItems.isEmpty()) {
            throw new NotFoundException("No menu found for this date.");
        }
        Map<Restaurant, List<MenuItem>> collect = menuItems.stream()
                .collect(Collectors.groupingBy(MenuItem::getRestaurant));
        List<MenuDtoForUser> list = new ArrayList<>();
        collect.entrySet().forEach(el -> {
            List<DishDtoForUser> collect1 = el.getValue().stream().map(el1 -> new DishDtoForUser(el1.getDish().getId(), el1.getDish().getName(),
                    el1.getDish().getDescription(), el1.getPrice())).collect(Collectors.toList());
            list.add(new MenuDtoForUser(DateTimeUtil.getParseDateString(date),
                    RestaurantUtil.createDtoFrom(el.getKey()), collect1));
        });
        return list;
    }

    @GetMapping(value = "/votes/{date}/votingresults")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResultObject> getVotesResults(@PathVariable("date") String date) {
        logger.info("get restaurants and voices for this date");
        List<ResultObject> resultByDate = voteRepository.getResultByDate(DateTimeUtil.getParseDateString(date).atStartOfDay());
        if (resultByDate.isEmpty()) {
            throw new NotFoundException("No voting results found for this date");
        }
        return resultByDate
                .stream().sorted((el1, el2) -> -el1.getVotes().compareTo(el2.getVotes()))
                .collect(Collectors.toList());

    }


}
