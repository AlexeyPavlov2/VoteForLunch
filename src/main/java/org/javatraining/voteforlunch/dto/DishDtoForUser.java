package org.javatraining.voteforlunch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DishDtoForUser {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
}
