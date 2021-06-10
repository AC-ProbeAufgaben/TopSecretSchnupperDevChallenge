package com.amiconsult.topsecretschnupperdevchallenge.controller;

import com.amiconsult.topsecretschnupperdevchallenge.exception.ResourceNotFoundException;
import com.amiconsult.topsecretschnupperdevchallenge.model.FavFood;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriendsService;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/friends")
public class FoodFriendsController {

    @Autowired
    FoodFriendsRepository foodFriendsRepository;

    @Autowired
    FavFoodRepository favFoodRepository;

    @Autowired
    FoodFriendsService  foodFriendsService;

    //get all friends
    @GetMapping("all")
    public List<FoodFriends> getAllFriends() {
        return foodFriendsRepository.findAll();
    }

    //get friend by id
    @GetMapping("{id}")
    public FoodFriends getFriendById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
            FoodFriends friend = foodFriendsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Friend not found for id: " + id));
            return friend;
    }

    //get friend by first name
    @GetMapping("/name/{name}")
    public FoodFriends getFriendByName(@PathVariable String name) throws ResourceNotFoundException {
            FoodFriends friend = Optional.ofNullable(foodFriendsRepository.findByNameIgnoreCase(name))
                    .orElseThrow(() -> new ResourceNotFoundException("Friend not found for name: " + name));

//         TODO: Why are optionals better than if statements, like below
//              if (friend.isEmpty()) { throw new ResourceNotFoundException("Friend not found for name: " + name); }

            return friend;
    }

    // add new friend
    @PostMapping("add")
    public ResponseEntity<?> postFriend(@RequestBody FoodFriends friend) {
        if (foodFriendsService.checkName(friend)) {
            return ResponseEntity.badRequest().body("Due to a lack of noticeable abilities you are not wanted");
        }
        FoodFriends newFriend = foodFriendsService.checkDbAndSave(friend);
        return ResponseEntity.ok(foodFriendsRepository.save(newFriend));
    }

    // get friends by their favorite food
    @GetMapping("/food/{food}")
    public ResponseEntity<?> getFriendsByFood(@PathVariable String food) throws ResourceNotFoundException {
        FavFood favFood = Optional.ofNullable(favFoodRepository.findByNameIgnoreCase(food))
                .orElseThrow(() -> new ResourceNotFoundException("Food not found: " + food));
        return ResponseEntity.ok(favFood.getFavorites());
    }

    // edit friend by Id
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editFriend(@RequestBody FoodFriends friend, @PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
            FoodFriends friendToUpdate = foodFriendsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Friend not found for id: " + id));

            foodFriendsRepository.save(friend); // REPLACES entity. Properties will be defined as NULL if not given.
            return ResponseEntity.ok().body("Friend Updated. mmmm Food.");
    }

    // delete friend by Id
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeFriend(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
            FoodFriends deletedFriend = foodFriendsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Friend not found for id: " + id));

            Set<FavFood> favFoodSet = deletedFriend.getFavFoods();

            if (favFoodSet != null) {
                for (FavFood favFood : favFoodSet) {
                    favFood.removeFoodFriend(deletedFriend);
                }
            }

            foodFriendsRepository.delete(deletedFriend);
            return ResponseEntity.ok().body("Bye Bye " + deletedFriend.getName());
    }
}


