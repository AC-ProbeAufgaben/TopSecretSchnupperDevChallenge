package com.amiconsult.topsecretschnupperdevchallenge.model;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FoodFriendsService {
    public boolean checkName(FoodFriends friend) {

        boolean badName = false;

        List<String> nameList = new ArrayList<>(
                Arrays.asList("Hawkeye", "Clint","Francis","Barton","Sören", "Soeren")
        );

        System.out.println("new friend's name is: " + friend.getName());

        for (String name : nameList) {
            if (friend.getName().equals(name)) {
                badName = true;
                break;
            }
        }
        return badName;
    }
}
