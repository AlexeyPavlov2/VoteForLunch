package org.javatraining.voteforlunch;

import org.javatraining.voteforlunch.service.dish.DishServiceTest;
import org.javatraining.voteforlunch.service.menu_item.MenuItemServiceTest;
import org.javatraining.voteforlunch.service.restaurant.RestaurantServiceTest;
import org.javatraining.voteforlunch.service.role.RoleServiceTest;
import org.javatraining.voteforlunch.service.user.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DishServiceTest.class,
        MenuItemServiceTest.class,
        RestaurantServiceTest.class,
        RoleServiceTest.class,
        UserServiceTest.class
    }
)


public class SuitTest {
}
