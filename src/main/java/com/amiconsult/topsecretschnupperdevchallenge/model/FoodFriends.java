package com.amiconsult.topsecretschnupperdevchallenge.model;

import javax.persistence.*;

@Entity
@Table(name = "food_friends")
public class FoodFriends {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY) // --- 1)
    private int id;

    @Column(name= "name")
    private String name;

    @Column(name= "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "fav_food")
    private String favFood;

    public FoodFriends(String name, String lastName, String email, int age, String favFood) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.favFood = favFood;
    }

    public FoodFriends() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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


}
