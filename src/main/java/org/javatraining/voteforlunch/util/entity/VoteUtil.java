package org.javatraining.voteforlunch.util.entity;

import org.javatraining.voteforlunch.dto.UserDto;
import org.javatraining.voteforlunch.dto.VoteDto;
import org.javatraining.voteforlunch.model.Restaurant;
import org.javatraining.voteforlunch.model.User;
import org.javatraining.voteforlunch.model.Vote;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.service.restaurant.RestaurantService;
import org.javatraining.voteforlunch.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoteUtil implements EntityUtil<Vote, VoteDto> {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public VoteDto createDtoFromEntity(Vote vote) {
        return new VoteDto(vote.getId(), vote.getDatev(), vote.getUser().getId(),
                vote.getRestaurant().getId());
    }

    @Override
    public Vote createEntityFromDto(VoteDto voteDto) {
        return new Vote(voteDto.getId(), voteDto.getDatev(),
                userService.read(voteDto.getUser_id()), restaurantService.read(voteDto.getRestaurant_id()));
    }

    @Override
    public Vote updateEntityFromDto(Vote vote, VoteDto voteDto) {
        Restaurant restaurant = restaurantService.read(voteDto.getRestaurant_id());
        vote.setRestaurant(restaurant);
        return voteRepository.save(vote);
    }

    @Override
    public Vote createNewEntityFromAnother(Vote vote) {
        return new Vote(vote.getId(), vote.getDatev(), vote.getUser(), vote.getRestaurant());
    }
}
