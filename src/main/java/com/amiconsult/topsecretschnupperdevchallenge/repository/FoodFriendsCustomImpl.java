package com.amiconsult.topsecretschnupperdevchallenge.repository;

import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoodFriendsCustomImpl implements FoodFriendsRepositoryCustom {

    public boolean checkBadNames(FoodFriends friend) {
        List<String> nameList = new ArrayList<>(
                Arrays.asList("Hawkeye", "Clint","Francis","Barton","Sören", "Soeren")
        );

        System.out.println("new friend's name is: " + friend.getName()); // friend.getName() == Sören

        for (String name : nameList) {
            System.out.println(name);
            if (friend.getName().equals(name)) { // ==
                return false;
            }
        }
        return true;
    }

/*
 TODO: method bodies
    {
        String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
        return this.findByName(capName);
    }

    public FoodFriends findByName(String name) {
        String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
        return this.findByName(capName);
    }
*/

}
