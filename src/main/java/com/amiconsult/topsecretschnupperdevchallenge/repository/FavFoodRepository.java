package com.amiconsult.topsecretschnupperdevchallenge.repository;

import com.amiconsult.topsecretschnupperdevchallenge.model.FavFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavFoodRepository extends JpaRepository<FavFood, Long> {

    FavFood findByName(String name);
    FavFood findByNameIgnoreCase(String name);

}
