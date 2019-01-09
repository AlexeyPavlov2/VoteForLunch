package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.dto.UserDto;
import org.javatraining.voteforlunch.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;

import static org.javatraining.voteforlunch.util.entity.UserUtil.createDtoFrom;
import static org.javatraining.voteforlunch.util.entity.UserUtil.createUserFromDto;

@RestController
@RequestMapping(value = LoginController.REST_URL)
public class LoginController {
    static final String REST_URL = "/public/register";
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto, UriComponentsBuilder ucBuilder) {
        logger.info("Register new user with info {}", userDto);
        UserDto created = createDtoFrom(userService.create(createUserFromDto(userDto)));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/public/register" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
