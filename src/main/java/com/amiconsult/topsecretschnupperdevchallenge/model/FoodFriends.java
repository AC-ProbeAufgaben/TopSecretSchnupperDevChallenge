package com.amiconsult.topsecretschnupperdevchallenge.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

//    public FoodFriends(Long id, String name, String lastName, String password, String email, boolean active, String role, Set<FavFood> favFoods) {
//        this.id = id;
//        this.name = name;
//        this.lastName = lastName;
//        this.password = password;
//        this.email = email;
//        this.active = active;
//        this.role = role;
//        this.favFoods = favFoods;
//    }

    public void addFavFood(FavFood favFood) {
        favFoods.add(favFood);
    }

    public void removeFavFood(FavFood favFood) {
        favFoods.remove(favFood);;
    }


}
