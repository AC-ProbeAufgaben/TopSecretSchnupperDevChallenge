package com.amiconsult.topsecretschnupperdevchallenge.repository;

import com.amiconsult.topsecretschnupperdevchallenge.model.FavFood;
import com.amiconsult.topsecretschnupperdevchallenge.model.FoodFriends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavFoodRepository extends JpaRepository<FavFood, Long> {

    FavFood findByName(String name);
    FavFood findByNameIgnoreCase(String name);
}
