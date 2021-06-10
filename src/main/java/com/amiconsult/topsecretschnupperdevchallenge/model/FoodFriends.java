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

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private boolean active;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "favorites", cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<FavFood> favFoods = new HashSet<>();

    public FoodFriends(Long id, String name, String lastName, String password, String email, boolean active, String role, Set<FavFood> favFoods) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.active = active;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<FavFood> getFavFoods() {
        return favFoods;
    }

    public void setFavFoods(Set<FavFood> favFoods) {
        this.favFoods = favFoods;
    }

    public void addFavFood(FavFood favFood) {
        favFoods.add(favFood);
    }

    public void removeFavFood(FavFood favFood) {
        favFoods.remove(favFood);;
    }


}
