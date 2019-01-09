package org.javatraining.voteforlunch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto implements Serializable {
    private Integer id;
    private LocalDateTime datev;
    private int user_id;
    private int restaurant_id;
}
