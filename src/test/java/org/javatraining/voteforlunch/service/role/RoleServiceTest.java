package org.javatraining.voteforlunch.service.role;

import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Role;
import org.javatraining.voteforlunch.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.javatraining.voteforlunch.util.RoleTestData.*;
import static org.javatraining.voteforlunch.util.entity.RoleUtil.createNewFromAnother;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RoleServiceTest {
    @Autowired
    private RoleService service;

    @Autowired
    CacheManager cacheManager;

    @Before
    public void beforeEach() throws Exception {
        cacheManager.getCache("users").clear();
        cacheManager.getCache("roles").clear();
    }


    @Test
    public void create() {
        TestUtil.assertMatch(service.create(createNewFromAnother(ROLE_3)), ROLE_3);

    }

    @Test
    public void read() {
        TestUtil.assertMatch(service.read(ROLE_1_ID), ROLE_1);
    }

    @Test
    public void readAll() {
        TestUtil.assertMatch(service.readAll(), ROLES);
    }

    @Test
    public void update() {
        String name = "SPECIALUSER";
        Role role = service.read(ROLE_1_ID);
        role.setName(name);
        System.out.println("roleeeeee " + service.update(role));
        assertEquals(name, service.update(role).getName());
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(ROLE_3);
    }

    @Test
    public void delete() {
        service.delete(ROLE_1_ID);
        List<Role> actual = service.readAll();
        List<Role> expected = Arrays.asList(ROLE_2);
        TestUtil.assertMatch(actual, expected,"registered", "roles");
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(ROLE_3_ID);
    }

    @Test
    public void deleteAll() {
        service.deleteAll();
        assertTrue(service.readAll().isEmpty());
    }

    @Test
    public void readByName() {
        TestUtil.assertMatch(service.readByName("ROLE_ADMIN"), ROLE_2);
    }

    @Test(expected = NotFoundException.class)
    public void readByNameNotFound() {
        TestUtil.assertMatch(service.readByName("SUPERADMIN"), ROLE_2);
    }

}