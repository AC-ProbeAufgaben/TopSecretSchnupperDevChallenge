package com.amiconsult.topsecretschnupperdevchallenge.repository;

import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodFriendsRepositoryBasic extends JpaRepository<FoodFriends, Integer> {
    public FoodFriends findByName(String name);
}
