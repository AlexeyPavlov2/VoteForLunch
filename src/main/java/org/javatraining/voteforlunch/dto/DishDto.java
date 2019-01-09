package org.javatraining.voteforlunch.dto;

import lombok.*;
import org.javatraining.voteforlunch.model.Restaurant;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    private Integer id;
    private String name;
    private String description;
}
