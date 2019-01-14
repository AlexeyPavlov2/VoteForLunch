package org.javatraining.voteforlunch.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.javatraining.voteforlunch.dto.UserDto;
import org.javatraining.voteforlunch.exception.NotFoundException;
import org.javatraining.voteforlunch.model.Role;
import org.javatraining.voteforlunch.service.user.UserService;
import org.javatraining.voteforlunch.util.entity.UserUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.javatraining.voteforlunch.util.RoleTestData.ROLE_2_ID;
import static org.javatraining.voteforlunch.util.TestUtil.assertMatch;
import static org.javatraining.voteforlunch.util.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserAdminControllerTest extends AbstractControllerTest {
    private String REST_URL = "/admin/users";

    @Autowired
    @Qualifier("getMapper")
    private ObjectMapper mapper;

    @Autowired
    private UserService userService;

    @Test
    public void testGetUnAuth() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + USER_1_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void readOne() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + USER_1_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, UserDto.class),
                        UserUtil.createDtoFrom(userService.read(USER_1_ID))));
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void readAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(UserUtil.createDtoListFromUserList(userService.readAll())));
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void readAllSortName() throws Exception {
        List<UserDto> expected = UserUtil.createDtoListFromUserList(userService.readAllOrderByName());
        mockMvc.perform(get(REST_URL + "/sort")
                .param("order", "name"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(expected));
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void readAllSortEmail() throws Exception {
        List<UserDto> expected = UserUtil.createDtoListFromUserList(userService.readAllOrderByEmail());
        mockMvc.perform(get(REST_URL + "/sort")
                .param("order", "email"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(expected));
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void readWithFiler() throws Exception {
        List<UserDto> expected = UserUtil.createDtoListFromUserList(userService.readByNameLike("al"));
        mockMvc.perform(get(REST_URL + "/filter")
                .param("name", "al"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(expected));
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void readPage() throws Exception {
        List<UserDto> expected = UserUtil.createDtoListFromUserList(userService.readPaginated(1, 2));
        mockMvc.perform(get(REST_URL + "/page")
                .param("offset", "1")
                .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getToMatcher(expected));
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void create() throws Exception {
        UserDto expected = UserUtil.createDtoFrom(USER_5);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());
        UserDto actual = readFromJsonResultActions(action, UserDto.class);
        assertMatch(actual, expected, "password", "registered", "roles");
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void setEnable() throws Exception {
        mockMvc.perform(post(REST_URL + "/" + USER_2_ID)
                .param("enabled", "false"))
                .andDo(print());
        Assert.assertFalse(userService.read(USER_2_ID).isEnabled());
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void update() throws Exception {
        UserDto expected = UserUtil.createDtoFrom(USER_2);
        expected.setEmail("updated" + USER_2.getEmail());
        ResultActions action = mockMvc.perform(put(REST_URL + "/" + USER_2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(expected)))
                .andDo(print())
                .andExpect(status().isOk());
        UserDto actual = readFromJsonResultActions(action, UserDto.class);
        assertMatch(actual, expected, "password", "registered", "roles");
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void addRole() throws Exception {
        mockMvc.perform(post(REST_URL + "/" + USER_1_ID + "/roles/" + ROLE_2_ID))
                .andDo(print())
                .andExpect(status().isCreated());
        Assert.assertTrue(userService.read(USER_1_ID).getRoles().stream().filter(el -> el.getId().equals(ROLE_2_ID)).count() > 0);
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void deleteRole() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + USER_2_ID + "/roles/" + ROLE_2_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assert.assertEquals(0, userService.read(USER_2_ID).getRoles().stream().filter(el -> el.getId().equals(ROLE_2_ID)).count());
    }

    @Test
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void deleteAllUsers() throws Exception {
        mockMvc.perform(delete(REST_URL ))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assert.assertTrue(userService.readAll().isEmpty());
    }

    @Test(expected = NotFoundException.class)
    @WithMockUser(username = "alex", password = "qwerty2", roles = {"USER", "ADMIN"})
    public void deleteOne() throws Exception {
        mockMvc.perform(delete(REST_URL + "/" + USER_3_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        userService.read(USER_3_ID);
    }








    public <T> String writeValue(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = mapper.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }


    public <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return readValue(getContent(result), clazz);
    }


    public String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public <T> List<T> readListFromJsonMvcResult(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException {
        return readValues(getContent(result), clazz);
    }

    public <T> ResultMatcher getToMatcher(T... expected) {
        return getToMatcher(List.of(expected));
    }

    public <T> T readFromJsonResultActions(ResultActions action, Class<T> clazz) throws UnsupportedEncodingException {
        return readFromJsonMvcResult(action.andReturn(), clazz);
    }


    public ResultMatcher getToMatcher(Iterable<UserDto> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, UserDto.class)).isEqualTo(expected);
    }

    public ResultMatcher getToMatcherRoles(Iterable<Role> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, Role.class)).isEqualTo(expected);
    }


}