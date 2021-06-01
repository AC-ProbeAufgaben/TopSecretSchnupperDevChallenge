package com.amiconsult.topsecretschnupperdevchallenge.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "FOOD_FRIENDS")
public class FoodFriends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name")
    private String name;

    @Column(name= "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String email;

    @Column(name = "fav_food")
    private String favFood;

    @ManyToMany(targetEntity = FavFood.class, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "friends_foods",
            joinColumns = @JoinColumn(name = "friend_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id"))
    Set<FavFood> favFoods = new HashSet<>();

    public FoodFriends(Long id, String name, String lastName, int age, String email, String favFood, Set<FavFood> favFoods) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.favFood = favFood;
        this.favFoods = favFoods;
    }

    public FoodFriends() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFavFood() {
        return favFood;
    }

    public void setFavFood(String favFood) {
        this.favFood = favFood;
    }

    public Set<FavFood> getFavFoods() {
        return favFoods;
    }

    public void setFavFoods(Set<FavFood> favFoods) {
        this.favFoods = favFoods;
    }

    public void addFavFood(FavFood favFood) {
        favFoods.add(favFood);
        favFood.getFavorites().add(this);
    }

    public void removeFavFood(FavFood favFood) {
        favFoods.remove(favFood);
        favFood.getFavorites().remove(this);
    }
}
