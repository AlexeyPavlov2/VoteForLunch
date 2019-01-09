package org.javatraining.voteforlunch.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
//@JsonAutoDetect(fieldVisibility = ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class AbstractBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;




}
