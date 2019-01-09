package org.javatraining.voteforlunch.config.security;

import org.javatraining.voteforlunch.dto.UserDto;
import org.javatraining.voteforlunch.model.User;
import org.javatraining.voteforlunch.util.entity.UserUtil;

public class AuthorizedUser /*extends org.springframework.security.core.userdetails.User*/ {
    private static final long serialVersionUID = 1L;

    private UserDto userTo;

    public AuthorizedUser(User user) {
        //super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.createDtoFrom(user);
    }

    public int getId() {
        return userTo.getId();
    }

    public void update(UserDto newDto) {
        userTo = newDto;
    }

    public UserDto getUserTo() {
        return userTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }


}