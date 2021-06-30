package com.amiconsult.topsecretschnupperdevchallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "FAV_FOODS")
public class FavFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name")
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "friends_fav_foods",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    @JsonIgnore
    private Set<FoodFriends> favorites = new HashSet<>();

    public void addFoodFriend(FoodFriends foodFriend) {
        favorites.add(foodFriend);
    }

    public void removeFoodFriend(FoodFriends foodFriend) {
        favorites.remove(foodFriend);
    }
}
