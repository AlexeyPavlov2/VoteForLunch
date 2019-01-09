package org.javatraining.voteforlunch.dto;

import lombok.*;
import org.javatraining.voteforlunch.model.Dish;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private Integer id;
    private String name;
    private String description;
    private String contact;
    private String site;
    private String email;
    private String phones;

}
