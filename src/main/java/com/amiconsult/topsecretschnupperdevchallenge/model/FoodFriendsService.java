package com.amiconsult.topsecretschnupperdevchallenge.model;

import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;


@Service
public class FoodFriendsService {

    @Autowired
    FavFoodRepository favFoodRepository;

    @Autowired
    FoodFriendsRepository foodFriendsRepository;

    @Autowired
    BCryptPasswordEncoder bcrypt;

    public List<FoodFriends> findAllFriends() {
        return foodFriendsRepository.findAll();
    }

    public boolean checkName(FoodFriends friend) {

        boolean badName = false;

        List<String> nameList = new ArrayList<>(
                Arrays.asList("Hawkeye", "Clint","Francis","Barton","Sören", "Soeren")
        );


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
        newFriend.setPassword(bcrypt.encode(friend.getPassword()));
        newFriend.setActive(true);
        newFriend.setRole("ROLE_USER");

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
        foodFriendsRepository.save(newFriend);
        return newFriend;
    }

    public Optional<FoodFriends> updateFoodFriend(FoodFriends friendToUpdate, FoodFriends updatedFriend) {

        // Remove old foods
        Set<FavFood> foods = friendToUpdate.getFavFoods();
        Set<FavFood> updatedFriendsFoodsToCheck = updatedFriend.getFavFoods();

        Set<FavFood> foodsToRemove = new HashSet<>();

        for (FavFood food : updatedFriendsFoodsToCheck) {
            FavFood foodToRemove = new FavFood();
            if (!foods.contains(food)) {
                foodToRemove = food;
            }
            if (foodToRemove.getName() != null) {
                foodsToRemove.add(foodToRemove);
                foodToRemove.removeFoodFriend(updatedFriend);
            }
        }
        updatedFriend.getFavFoods().removeAll(foodsToRemove);

        // Update friend's new foods
        return Optional.of(updatedFriend)
            .map(friend -> {
                friend.setName(friendToUpdate.getName());
                friend.setLastName(friendToUpdate.getLastName());
                friend.setEmail(friendToUpdate.getEmail());

                for (FavFood food : friendToUpdate.getFavFoods()) {

                    FavFood favoritedFood;

                    if (favFoodRepository.findByName(food.getName()) == null) {
                        favoritedFood = new FavFood();
                        favoritedFood.setName(food.getName());
                        favFoodRepository.save(favoritedFood);
                    } else {
                        favoritedFood = favFoodRepository.findByName(food.getName());
                    }

                    friend.addFavFood(favoritedFood);
                    favoritedFood.addFoodFriend(friend);
                }
                return friend;
            });
    }

    public void deleteFriendsFoods(FoodFriends deletedFriend) {
        Set<FavFood> favFoodSet = deletedFriend.getFavFoods();

        if (favFoodSet != null) {
            for (FavFood favFood : favFoodSet) {
                favFood.removeFoodFriend(deletedFriend);
            }
        }

        foodFriendsRepository.delete(deletedFriend);
    }

}
