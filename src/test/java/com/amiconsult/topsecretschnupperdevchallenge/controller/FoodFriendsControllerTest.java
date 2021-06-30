package com.amiconsult.topsecretschnupperdevchallenge.controller;


import com.amiconsult.topsecretschnupperdevchallenge.TopsecretschnupperdevchallengeApplication;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriendsService;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TopsecretschnupperdevchallengeApplication.class)
@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@WithMockUser
public class FoodFriendsControllerTest {

    private static FoodFriends mockFriend;

    @Autowired
    private FoodFriendsService foodFriendsService;
    @Autowired
    private FoodFriendsRepository foodFriendsRepository;
    @Autowired
    private FavFoodRepository favFoodRepository;
    @Autowired
    private FoodFriendsController foodFriendsController;
    @Autowired
    MockMvc mockMvc;

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String returnMvcResultAsString(MvcResult mvcResult) throws Exception {
        System.out.println("\n <><><>< MVC Result/Return Object ><><><>");

        String contentAsString = mvcResult.getResponse().getContentAsString();

        return contentAsString;
    }

    @Test
    public void getFriendByName()
            throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/friends/name/Steve")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Matchers.is("Steve")))
                .andReturn();

        System.out.println(returnMvcResultAsString(mvcResult));
    }

    @Test
    public void getAllFriends() throws Exception{

        MvcResult mvcResult = mockMvc.perform(get("/friends/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)))
                .andReturn();

        System.out.println(returnMvcResultAsString(mvcResult));

    }

    @Test
    public void getFriendById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/friends/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andReturn();

        System.out.println(returnMvcResultAsString(mvcResult));
    }

    @Test
    public void postFriend() throws Exception {
        mockFriend = new FoodFriends().setName("Tom").setActive(true).setLastName("Dude").setEmail("dude@dude.com").setPassword("pass");

        MvcResult mvcResult = mockMvc.perform(post("/friends/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mockFriend))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", Matchers.is("dude@dude.com")))
                .andDo(print())
                .andReturn();

        System.out.println(returnMvcResultAsString(mvcResult));
    }

    @Test
    public void getFriendsByFood() throws Exception {

        mockMvc.perform(post("/friends/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Turtle\", \"lastName\": \"Canger\", \"password\": \"pass\", \"email\": \"tom@dude.com\", \"active\": false, \"role\": null, \"favFoodsList\": [\"steak\", \"fish\" ]}")
                .accept(MediaType.APPLICATION_JSON))
                ;

        MvcResult mvcResult = mockMvc.perform(get("/friends/food/steak")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", Matchers.is("Turtle")))
                .andReturn();

        System.out.println(returnMvcResultAsString(mvcResult));
    }
//
//    @Test
//    public void removeFriend() {
//    }
}