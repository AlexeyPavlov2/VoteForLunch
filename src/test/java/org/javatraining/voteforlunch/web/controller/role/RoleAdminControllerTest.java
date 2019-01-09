package org.javatraining.voteforlunch.web.controller.role;

import org.javatraining.voteforlunch.model.Role;
import org.javatraining.voteforlunch.service.role.RoleService;
import org.javatraining.voteforlunch.util.entity.RoleUtil;
import org.javatraining.voteforlunch.web.controller.RoleAdminController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.javatraining.voteforlunch.util.RoleTestData.ROLE_1;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RoleAdminController.class)
//@AutoConfigureMockMvc
//@RestClientTest

public class RoleAdminControllerTest {
    private String REST_URL = "/admin/roles/";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleService roleService;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    public void getAll() {


    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void read() throws Exception {
        Role role = RoleUtil.createNewFromAnother(ROLE_1);

        System.out.println("REST::: " + REST_URL + role.getId());
        mvc.perform(get(REST_URL + role.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());


    }

    @Test
    public void read1() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void delete() {
    }
}