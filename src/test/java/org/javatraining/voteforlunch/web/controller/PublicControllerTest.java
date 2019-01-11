package org.javatraining.voteforlunch.web.controller;

import org.javatraining.voteforlunch.service.restaurant.RestaurantService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.javatraining.voteforlunch.util.UserTestData.USER_2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PublicControllerTest extends AbstractControllerTest {
    private String REST_URL = "/public";
    private String DATE_PART = "20181229";
    private String DATE_PART_WRONG = "20181220";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void getMenuNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + "/menu/" + DATE_PART_WRONG))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("No menu found for this date.")))
                .andDo(print());
}

    @Test
    public void getMenu() throws Exception {
        System.out.println("USER:   " + USER_2);
     //   System.out.println("REST: " + RESTAURANT_1);
        mockMvc.perform(get(REST_URL + "/menu/" + DATE_PART))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    /*@Test
    public void getVotesResults() throws Exception {
        mockMvc.perform(get(REST_URL + "/votes/" + DATE_PART + "/votingresults"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(getToMatcherForResultObject(VOTES_RESULT_20181229));


    }*/


    /*private static ResultMatcher getToMatcherForResultObject(Iterable<ResultObject> expected) {
        //return result -> assertThat(readListFromJsonMvcResult(result, ResultObject.class)).isEqualTo(expected);
    }*/
}