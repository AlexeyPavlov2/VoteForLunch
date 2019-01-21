package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.model.Vote;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.javatraining.voteforlunch.util.RestaurantTestData.RESTAURANT_1_ID;
import static org.javatraining.voteforlunch.util.UserTestData.USER_1_ID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class LoggedUserControllerTest extends AbstractControllerTest {
    private final String REST_URL = "/profile";
    private static final Logger logger = LoggerFactory.getLogger(LoggedUserControllerTest.class);

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithMockUser(username = "mick", password = "qwerty1", roles = {"USER"})
    public void doVote() throws Exception {
        logger.info("Vote!");
        mockMvc.perform(post(REST_URL + "/vote/" + RESTAURANT_1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        Vote vote = voteRepository.findVotesByDateAndUserIdAndRestaurantId(
                LocalDate.now().atStartOfDay(), USER_1_ID, RESTAURANT_1_ID);
        Assert.assertNotNull(vote);
    }

    @Test
    @WithMockUser(username = "mick", password = "qwerty1", roles = {"USER"})
    public void updatePasswordWrongOldPassword() throws Exception {
        logger.info("Update password");
        mockMvc.perform(post(REST_URL + "/update_password")
                .param("userId", String.valueOf(USER_1_ID))
                .param("oldPassword", "qwerty")
                .param("newPassword", "qwerty11"))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("Invalid Old Password")));
    }

    @Test
    @WithMockUser(username = "mick", password = "qwerty1", roles = {"USER"})
    public void updatePassword() throws Exception {
        logger.info("Update password");
        mockMvc.perform(post(REST_URL + "/update_password")
                .param("userId", String.valueOf(USER_1_ID))
                .param("oldPassword", "qwerty1")
                .param("newPassword", "qwerty11"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}