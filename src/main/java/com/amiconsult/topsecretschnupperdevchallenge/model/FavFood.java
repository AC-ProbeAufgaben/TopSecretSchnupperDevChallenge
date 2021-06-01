package com.amiconsult.topsecretschnupperdevchallenge.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FAV_FOODS")
public class FavFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name")
    private String name;

    @ManyToMany(targetEntity = FoodFriends.class, mappedBy = "favFoods", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<FoodFriends> favorites = new HashSet<>();

    public FavFood(Long id, String name, Set<FoodFriends> favorites) {
        this.id = id;
        this.name = name;
        this.favorites = favorites;
    }

    public FavFood(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public FavFood() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<FoodFriends> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<FoodFriends> favorites) {
        this.favorites = favorites;
    }

    public void addFoodFriend(FoodFriends foodFriend) {
        favorites.add(foodFriend);
    }
}
