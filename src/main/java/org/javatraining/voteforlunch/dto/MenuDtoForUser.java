package org.javatraining.voteforlunch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.javatraining.voteforlunch.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class MenuDtoForUser {
    private LocalDate date;
    private RestaurantDto restaurant;
    private List<DishDtoForUser> dishes;
}
