package org.javatraining.voteforlunch.repository;

import org.javatraining.voteforlunch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);

    @Transactional
    @Modifying
    int removeById(int id);

    List<User> findByNameIgnoreCaseContaining(String name);


}
