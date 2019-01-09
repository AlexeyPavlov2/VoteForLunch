package org.javatraining.voteforlunch.util.entity;

import org.javatraining.voteforlunch.model.Role;

public class RoleUtil {
    public static Role createNewFromAnother(Role role) {
        return new Role(role.getId(), role.getName());
    }
}
