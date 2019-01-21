package org.javatraining.voteforlunch.repository;

import org.javatraining.voteforlunch.dto.ResultObject;
import org.javatraining.voteforlunch.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    void removeByDatev(LocalDateTime date);

    List<Vote> findVotesByDatev(LocalDateTime date);

    boolean existsVotesByDatev(LocalDateTime date);

    //https://stackoverflow.com/a/47471486/9632963
    @Query("SELECT new org.javatraining.voteforlunch.dto.ResultObject(v.restaurant.name, COUNT(v)) FROM Vote v WHERE EXTRACT(YEAR FROM datev ) = EXTRACT(YEAR FROM :date) AND EXTRACT(MONTH FROM datev ) = EXTRACT(MONTH FROM :date) AND EXTRACT(DAY FROM datev ) = EXTRACT(DAY FROM :date) GROUP BY v.restaurant.name")
    List<ResultObject> getResultByDate(@Param("date") LocalDateTime date);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE EXTRACT(YEAR FROM datev) = EXTRACT(YEAR FROM :date) AND EXTRACT(MONTH FROM datev ) = EXTRACT(MONTH FROM :date) AND EXTRACT(DAY FROM datev ) = EXTRACT(DAY FROM :date) and v.user.id = :userId")
    void removeByDateAndUserId(@Param("date") LocalDateTime date, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE EXTRACT(YEAR FROM datev) = EXTRACT(YEAR FROM :date) AND EXTRACT(MONTH FROM datev ) = EXTRACT(MONTH FROM :date) AND EXTRACT(DAY FROM datev ) = EXTRACT(DAY FROM :date) and v.user.id = :userId AND v.restaurant.id = :restaurantId")
    Vote findVotesByDateAndUserIdAndRestaurantId(
            @Param("date") LocalDateTime date, @Param("userId") int userId, @Param("restaurantId") int restaurantId);

}
