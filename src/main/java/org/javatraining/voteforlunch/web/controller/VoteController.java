package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.exception.TimeExpiredExeption;
import org.javatraining.voteforlunch.model.User;
import org.javatraining.voteforlunch.model.Vote;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.service.restaurant.RestaurantService;
import org.javatraining.voteforlunch.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL)
public class VoteController {
    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);
    static final String REST_URL = "/vote";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/{restaurantId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void doVote(@PathVariable("restaurantId") int restaurantId ) {
        logger.info("Vote!");
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime expiredDateTime = LocalDateTime.now().with(LocalTime.of(11, 0));
        if (dateTime.isAfter(expiredDateTime)) {
            throw new TimeExpiredExeption("User can vote up to 11 hours");
        }

        /*String currentUserName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        User user = userService.readByName(currentUserName);*/

        User user = userService.read(1);
        voteRepository.removeByDateAndUserId(LocalDate.now().atStartOfDay(),
                user.getId());
        voteRepository.save(new Vote(0, dateTime, user, restaurantService.read(restaurantId)));
    }


}
