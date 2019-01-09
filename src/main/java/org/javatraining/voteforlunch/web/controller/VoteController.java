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

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping(value = VoteController.REST_URL)
public class VoteController {
    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);
    static final String REST_URL = "/profile";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/vote/{restaurantId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void doVote(@PathVariable("restaurantId") int restaurantId ) {
        logger.info("Vote!");
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime expiredDateTime = LocalDateTime.now().with(LocalTime.of(11, 0));
        /*if (dateTime.isAfter(expiredDateTime)) {
            throw new TimeExpiredExeption("User can vote until 11 am");
        }*/

        String currentUserName = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
            logger.info(":doVote - Current user name: " + currentUserName);
        }

        User user = userService.readByName(currentUserName);

        voteRepository.removeByDateAndUserId(LocalDate.now().atStartOfDay(), user.getId());
        voteRepository.save(new Vote(0, dateTime, user, restaurantService.read(restaurantId)));
    }

}
