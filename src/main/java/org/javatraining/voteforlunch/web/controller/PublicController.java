package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.dto.MenuDtoForUser;
import org.javatraining.voteforlunch.dto.ResultObject;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.service.menu_item.MenuItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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

    @GetMapping(value = "/menu/{date}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<MenuDtoForUser> getMenu(@PathVariable("date") LocalDate date) {
        logger.info("Get the menu for the specified date");
        return menuItemService.getMenuForDate(date);
    }

    @GetMapping(value = "/votes/{date}/votingresults")
    @ResponseStatus(value = HttpStatus.OK)
    public List<ResultObject> getVotesResults(@PathVariable("date") LocalDate date) {
        logger.info("get restaurants and voices for this date");
        List<ResultObject> resultByDate = voteRepository.getResultByDate(date.atStartOfDay());
        if (resultByDate.isEmpty()) {
            throw new NotFoundException("No voting results found for this date");
        }
        return resultByDate
                .stream().sorted((el1, el2) -> -el1.getVotes().compareTo(el2.getVotes()))
                .collect(Collectors.toList());

    }


}
