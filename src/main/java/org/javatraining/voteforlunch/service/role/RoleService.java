package org.javatraining.voteforlunch.service.role;

import org.javatraining.voteforlunch.service.BaseService;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Role;

public interface RoleService extends BaseService<Role> {
    Role readByName(String name) throws NotFoundException;

}
