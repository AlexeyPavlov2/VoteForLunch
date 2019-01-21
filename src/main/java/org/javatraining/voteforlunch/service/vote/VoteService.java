package org.javatraining.voteforlunch.service.vote;

import org.javatraining.voteforlunch.model.Vote;
import org.javatraining.voteforlunch.service.BaseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VoteService extends BaseService<Vote> {

    void deleteByDate(LocalDate date);
    void deleteByUserId(int id);
    void deleteByRestaurantId(int id);
    List<Vote> findVotesByDatevAndUserId(LocalDateTime date, int userId);
    List<Vote> findVotesByDatevAndRestaurantId(LocalDateTime dateTime, int resturantId);

}
