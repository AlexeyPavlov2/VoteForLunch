package org.javatraining.voteforlunch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role /*implements GrantedAuthority*/ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(min=3, message="Name should have at least 3 characters")
    private String name;

    /*@Override
    public String getAuthority() {
        return name;
    }*/
}

