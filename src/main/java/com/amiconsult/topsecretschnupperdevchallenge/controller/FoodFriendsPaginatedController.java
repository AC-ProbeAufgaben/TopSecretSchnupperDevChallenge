package com.amiconsult.topsecretschnupperdevchallenge.controller;


import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriendsService;
import com.amiconsult.topsecretschnupperdevchallenge.repository.FavFoodRepository;
import com.amiconsult.topsecretschnupperdevchallenge.repository.PaginatedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/search")
public class FoodFriendsPaginatedController {

    @Autowired
    PaginatedRepository paginatedRepository;

    @Autowired
    FavFoodRepository favFoodRepository;

    @Autowired
    FoodFriendsService foodFriendsService;

    @Autowired
    BCryptPasswordEncoder bcrypt;

    @GetMapping("/friends")
    public ResponseEntity<Map<String, Object>> getAllTutorials(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
        ) {

        try {
            List<FoodFriends> allFriends;
            Pageable paging;
            String[] foodSearch = new String[2];
            String[] activeSearch = new String[2];
            Page<FoodFriends> pageFriends;

            if (direction != null && direction.equals("desc")) {
                paging = PageRequest.of(page, size, Sort.by("id").descending());
            } else if (direction != null && direction.equals("asc")) {
                paging = PageRequest.of(page, size, Sort.by("id").ascending());
            } else {
                paging = PageRequest.of(page, size);
            }

            System.out.println(name);

//            switch (name) {
//                case null:
//
//                    break
//            }

            if (name == null) {
                pageFriends = paginatedRepository.findAll(paging);
            } else if (name.length() >=1 && !name.startsWith("food?=") && !name.startsWith("active?=")) {
                pageFriends = paginatedRepository.findByNameContainingIgnoreCase(name, paging);
            } else if (name.startsWith("food?=")) {
                foodSearch = name.split("=");
                String food = foodSearch[1];
                pageFriends = paginatedRepository.findByFavFoodsName(food, paging);
            } else if (name.startsWith("active?=")) {
                // make helper method from nested if
                activeSearch = name.split("=");
                String active = activeSearch[1];
                if (active.equals("true")) {
                    pageFriends = paginatedRepository.findByActiveTrue(paging);
                } else if (active.equals("false")) {
                    pageFriends = paginatedRepository.findByActiveFalse(paging);
                } else {
                    pageFriends = paginatedRepository.findAll(paging);
                }
            } else {
                pageFriends = paginatedRepository.findAll(paging);
            }

            allFriends = pageFriends.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("foodFriends", allFriends);
            response.put("currentPage", pageFriends.getNumber());
            response.put("totalItems", pageFriends.getTotalElements());
            response.put("totalPages", pageFriends.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
