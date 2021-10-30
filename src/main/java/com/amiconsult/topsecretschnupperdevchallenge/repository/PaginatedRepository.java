package com.amiconsult.topsecretschnupperdevchallenge.repository;

import com.amiconsult.topsecretschnupperdevchallenge.model.FavFood;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaginatedRepository extends JpaRepository<FoodFriends, Long> {
    Page<FoodFriends> findAll(Pageable pageable);
    Page<FoodFriends> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<FoodFriends> findByActiveTrue(Pageable pageable);
    Page<FoodFriends> findByActiveFalse(Pageable pageable);
    Page<FoodFriends> findByFavFoodsName(String name, Pageable pageable);
}
