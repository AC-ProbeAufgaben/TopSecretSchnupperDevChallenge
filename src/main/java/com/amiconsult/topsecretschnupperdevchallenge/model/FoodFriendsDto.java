package com.amiconsult.topsecretschnupperdevchallenge.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors
public class FoodFriendsDto {

    private String name;
    private String lastName;
    private String password;
    private String email;
    private List<String> favFoodsList = new ArrayList<>();

}
