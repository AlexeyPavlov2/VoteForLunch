package org.javatraining.voteforlunch.repository;

import org.javatraining.voteforlunch.model.Dish;
import org.javatraining.voteforlunch.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id = :id")
    int removeById(@Param("id") int id);

    @Transactional
    @Override
    @Modifying
    @Query("DELETE FROM Restaurant" )
    void deleteAll();


    Restaurant findByName(String name);




}
