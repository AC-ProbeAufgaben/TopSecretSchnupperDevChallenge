package com.amiconsult.topsecretschnupperdevchallenge.controller;

import com.amiconsult.topsecretschnupperdevchallenge.exception.ResourceNotFoundException;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FoodFriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FoodFriendsController {

    @Autowired
    private FoodFriendsRepository foodFriendsRepository;

    //get friend
    @GetMapping("all")
    public List<FoodFriends> getAllFriends() {
        return this.foodFriendsRepository.findAll();
    }

    //get friend by id
    @GetMapping("{id}")
    public ResponseEntity<FoodFriends> getFriendById(@PathVariable(value = "id") int id)
        throws ResourceNotFoundException {
            FoodFriends friend = foodFriendsRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Friend not found for id: " + id));
            return ResponseEntity.ok().body(friend);
    }

    //get friend by name
    @GetMapping("/name/{name}")
    public FoodFriends getFriendByName(@PathVariable String name) {
           return foodFriendsRepository.findByName(name);
    }

    // add new friend
    @PostMapping("add")
    public ResponseEntity<?> postFriend(@RequestBody FoodFriends friend) { // TODO: <Optional> would be the "clean" way

        if (foodFriendsRepository.checkBadNames(friend)) {
            return ResponseEntity.badRequest().body("Due to a lack of noticeable abilities you are not wanted");
        } else {
            FoodFriends newFriend = foodFriendsRepository.save(friend);
            return ResponseEntity.ok().body(newFriend);
        }
    }

}
