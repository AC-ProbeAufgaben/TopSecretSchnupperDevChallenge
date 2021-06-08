package com.amiconsult.topsecretschnupperdevchallenge.repository;

import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodFriendsRepository extends JpaRepository<FoodFriends, Long> {
    public FoodFriends findByNameIgnoreCase(String name);

    Optional<FoodFriends> findByEmail(String email);
}
