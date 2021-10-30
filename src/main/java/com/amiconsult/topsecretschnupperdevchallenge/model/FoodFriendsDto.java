package com.amiconsult.topsecretschnupperdevchallenge.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FoodFriendsDto {

    private String name;
    private String lastName;
    private String password;
    private String email;
    private Boolean active;
    private String role;
    private String securityQuestionId;
    private String securityAnswer;
    private List<String> favFoodsList = new ArrayList<>();

}
