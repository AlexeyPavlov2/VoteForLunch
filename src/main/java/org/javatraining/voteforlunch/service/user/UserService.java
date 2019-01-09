package org.javatraining.voteforlunch.service.user;

import org.javatraining.voteforlunch.model.User;
import org.javatraining.voteforlunch.service.BaseService;
import org.javatraining.voteforlunch.exception.NotFoundException;

import java.util.List;

public interface UserService extends BaseService<User> {
    User readByName(String name) throws NotFoundException;
    List<User> readAllOrderByName();
    List<User> readAllOrderByEmail();
    List<User> readByNameLike(String name);
    List<User> readPaginated(int page, int size);
    User setEnabled(int id, boolean enabled);

}
