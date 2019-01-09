package org.javatraining.voteforlunch.dto;

import lombok.*;
import org.javatraining.voteforlunch.model.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private boolean enabled = true;
}
