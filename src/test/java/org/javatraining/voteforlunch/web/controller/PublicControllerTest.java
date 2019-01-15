package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.dto.MenuDtoForUser;
import org.javatraining.voteforlunch.dto.ResultObject;
import org.javatraining.voteforlunch.repository.VoteRepository;
import org.javatraining.voteforlunch.service.menu_item.MenuItemService;
import org.javatraining.voteforlunch.util.DateTimeUtil;
import org.javatraining.voteforlunch.util.TestUtil;
import org.javatraining.voteforlunch.util.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PublicControllerTest extends AbstractControllerTest {
    private String REST_URL = "/public";
    private String DATE_PART = "2018-12-29";
    private String DATE_PART_WRONG = "2018-12-20";

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private JsonUtil util;

    @Test
    public void getMenuNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/" + DATE_PART_WRONG))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("No menu found for this date.")))
                .andDo(print());
    }

    @Test
    public void getMenu() throws Exception {
        MvcResult result = mockMvc.perform(get(REST_URL + "/menu/" + DATE_PART))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<MenuDtoForUser> actual = util.readListFromJsonMvcResult(result, MenuDtoForUser.class);
        List<MenuDtoForUser> expected  = menuItemService.getMenuForDate(DateTimeUtil.getParseDateString(DATE_PART));
        TestUtil.assertMatch(actual,  expected);
    }

    @Test
    public void getResults() throws Exception {
        List<ResultObject> expected = voteRepository.getResultByDate(DateTimeUtil.getParseDateString(DATE_PART).atStartOfDay())
                .stream().sorted((el1, el2) -> -el1.getVotes().compareTo(el2.getVotes()))
                .collect(Collectors.toList());
        MvcResult result = mockMvc.perform(get(REST_URL + "/votes/" + DATE_PART + "/votingresults"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<ResultObject> actual = util.readListFromJsonMvcResult(result, ResultObject.class)
                .stream().sorted((el1, el2) -> -el1.getVotes().compareTo(el2.getVotes())).collect(Collectors.toList());
        TestUtil.assertMatch(actual, expected);
    }


}