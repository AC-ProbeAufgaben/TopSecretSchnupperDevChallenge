package com.amiconsult.topsecretschnupperdevchallenge.model;


import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FoodFriendsService {

    @Autowired
    FavFoodRepository favFoodRepository;

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

    public FoodFriends checkDbAndSave(FoodFriends friend) {
        FoodFriends newFriend = new FoodFriends();
        newFriend.setName(friend.getName());
        newFriend.setLastName(friend.getLastName());
        newFriend.setEmail(friend.getEmail());

        for (FavFood food : friend.getFavFoods()) {

            FavFood favoritedFood;

            if (favFoodRepository.findByName(food.getName()) == null) {
                favoritedFood = new FavFood();
                favoritedFood.setName(food.getName());
                favFoodRepository.save(favoritedFood);
            } else {
                favoritedFood = favFoodRepository.findByName(food.getName());
            }

            newFriend.addFavFood(favoritedFood);
            favoritedFood.addFoodFriend(newFriend);

        }
        return newFriend;
    }
}
