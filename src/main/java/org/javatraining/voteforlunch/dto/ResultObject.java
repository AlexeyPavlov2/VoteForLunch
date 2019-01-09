package org.javatraining.voteforlunch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObject {
    private String restaurantName;
    private Long votes;
}
