package com.amiconsult.topsecretschnupperdevchallenge.controller;

import com.amiconsult.topsecretschnupperdevchallenge.exception.ResourceNotFoundException;
import com.amiconsult.topsecretschnupperdevchallenge.model.*;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/friends")
public class FoodFriendsController {

    private static final String FRIENDNOTFOUND = "Friend not found for id: ";

    @Autowired
    FoodFriendsRepository foodFriendsRepository;

    @Autowired
    FavFoodRepository favFoodRepository;

    @Autowired
    FoodFriendsService  foodFriendsService;

    //get all friends
    @GetMapping("all")
    public List<FoodFriends> getAllFriends() {

        return foodFriendsService.findAllFriends();
    }

    //get friend by id
    @GetMapping("{id}")
    public ResponseEntity<FoodFriends> getFriendById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
            FoodFriends friend = foodFriendsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(FRIENDNOTFOUND + id));

            return new ResponseEntity<>(friend, HttpStatus.OK);
    }

    //get friend by first name
    @GetMapping("/name/{name}")
    public ResponseEntity<FoodFriends> getFriendByName(@PathVariable String name) throws ResourceNotFoundException {
            FoodFriends friend = Optional.ofNullable(foodFriendsRepository.findByNameIgnoreCase(name))
                    .orElseThrow(() -> new ResourceNotFoundException("Friend not found for name: " + name));


           return new ResponseEntity<>(friend, HttpStatus.OK);
    }

    // add new friend
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/add")
    public ResponseEntity<FoodFriends> postFriend(@RequestBody FoodFriendsDto friendDto) throws ResourceNotFoundException {
        FoodFriends friend = FoodFriendsMapper.INSTANCE.fromDto(friendDto);

        if (foodFriendsService.checkName(friend)) {
           throw new ResourceNotFoundException("You are not wanted " + friend.getName());
        }

        return new ResponseEntity<>(foodFriendsService.checkDbAndSave(friend), HttpStatus.CREATED);
    }

    // get friends by their favorite food
    @GetMapping("/food/{food}")
    public ResponseEntity<Set<FoodFriends>> getFriendsByFood(@PathVariable String food) throws ResourceNotFoundException {
        FavFood favFood = Optional.ofNullable(favFoodRepository.findByNameIgnoreCase(food))
                .orElseThrow(() -> new ResourceNotFoundException("Food not found: " + food));
        return ResponseEntity.ok(favFood.getFavorites());
    }

    // edit friend by Id
    @PutMapping("/edit/{id}")
    public ResponseEntity<FoodFriends> editFriend(@RequestBody FoodFriendsDto friendDto, @PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
            FoodFriends friendToUpdate = FoodFriendsMapper.INSTANCE.fromDto(friendDto);

            FoodFriends updatedFriend = foodFriendsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(FRIENDNOTFOUND + id));

            foodFriendsService.updateFoodFriend(friendToUpdate, updatedFriend);

            return new ResponseEntity<>(foodFriendsRepository.save(updatedFriend), HttpStatus.OK);
    }

    // delete friend by Id
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeFriend(@PathVariable(value = "id") Long id)
        throws ResourceNotFoundException {
            FoodFriends deletedFriend = foodFriendsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FRIENDNOTFOUND + id));

            foodFriendsService.deleteFriendsFoods(deletedFriend);

            return ResponseEntity.ok().body("Bye Bye " + deletedFriend.getName());
    }
}


