package com.amiconsult.topsecretschnupperdevchallenge.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FoodFriendsDto {

    private String name;
    private String lastName;
    private String email;
    private List<String> favorites = new ArrayList<>();

}
