package org.javatraining.voteforlunch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDtoForUser {
    private LocalDate date;
    private RestaurantDto restaurant;
    private List<DishDtoForUser> dishes;
}
