package org.javatraining.voteforlunch.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 150)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne (optional=false/*, cascade=CascadeType.ALL*/)
    @JoinColumn (name="restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

}
