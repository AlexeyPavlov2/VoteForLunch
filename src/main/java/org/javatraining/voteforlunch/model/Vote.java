package org.javatraining.voteforlunch.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private LocalDateTime datev;

    @ManyToOne (optional=false)
    @JoinColumn (name="user_id", nullable = false)
    private User user;

    @ManyToOne (optional=false)
    @JoinColumn (name="restaurant_id", nullable = false)
    private Restaurant restaurant;

}
