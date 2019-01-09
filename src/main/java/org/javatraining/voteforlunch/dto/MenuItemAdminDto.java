package org.javatraining.voteforlunch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemAdminDto implements Serializable {
    private Integer id;
    private LocalDate date;
    private int restaurant_id;
    private int dish_id;
    private BigDecimal price;
}
