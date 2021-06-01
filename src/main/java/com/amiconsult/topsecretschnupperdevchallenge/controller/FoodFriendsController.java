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

//            if (friend.isEmpty()) { throw new ResourceNotFoundException("Friend not found for name: " + name); }
            return friend;
    }

    // add new friend
    @PostMapping("add")
    public ResponseEntity<String> postFriend(@RequestBody FoodFriends friend) {



//            for (FavFood food : friend.getFavFoods()) {
//                if (favFoodRepository.count() == 0) {
//                    break;
//                } else if (favFoodRepository.findByName(food.getName()).equals(food.getName())) {
//                    friend.addFavFood(food);
//                    food.addFoodFriend(friend);
//                    foodFriendsRepository.
//                }
//            }

            // Added foodFriendsService to take care of checkName logic.
           if (foodFriendsService.checkName(friend)) {
               return ResponseEntity.badRequest().body("Due to a lack of noticeable abilities you are not wanted");
           } else {
               foodFriendsRepository.save(friend);
               return ResponseEntity.ok().body("Abilities have been proven. Friend Added.");
           }
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
            foodFriendsRepository.delete(deletedFriend);
            return ResponseEntity.ok().body("Bye Bye " + deletedFriend.getName());
    }
}


