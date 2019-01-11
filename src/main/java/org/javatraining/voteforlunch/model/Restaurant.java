package org.javatraining.voteforlunch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(max = 50)
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "contact", nullable = false)
    @NotBlank
    @Size(max = 200)
    private String contact;

    @Column(name = "site", nullable = false)
    @NotBlank
    @Size(max = 50)
    private String site;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 50)
    private String email;

    @Column(name = "phones", nullable = false)
    @NotBlank
    @Size(max = 50)
    private String phones;

    @OneToMany (mappedBy="restaurant", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Dish> dishes;

    @OneToMany (mappedBy="restaurant", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MenuItem> menuItems;

    @OneToMany (mappedBy="restaurant", fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Vote> votes;

}
