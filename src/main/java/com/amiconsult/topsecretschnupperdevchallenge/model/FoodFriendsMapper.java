package com.amiconsult.topsecretschnupperdevchallenge.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper
public interface FoodFriendsMapper {

    FoodFriendsMapper INSTANCE = Mappers.getMapper( FoodFriendsMapper.class );

    @Mapping(source = "favFoods", target = "favFoodsList", qualifiedByName = "getFoods")
    FoodFriendsDto toDto(FoodFriends foodFriend);

    @Mapping(source = "favFoodsList", target = "favFoods", qualifiedByName = "getFavs")
    FoodFriends fromDto(FoodFriendsDto foodFriendDto);

    @Named("getFoods")
    default List<String> getFavFoodsList(Set<FavFood> favFoods) {
        List<String> favFoodsList = new ArrayList<>();
        for (FavFood favFood : favFoods) {
            favFoodsList.add(favFood.getName());
        }
        return favFoodsList;
    }

    @Named("getFavs")
    default Set<FavFood> getFavFoodsSet(List<String> favFoodList) {
        Set<FavFood> favFoodSet = new HashSet<>();

        for (String food : favFoodList) {
            FavFood favFood = new FavFood();
            favFood.setName(food);
            favFoodSet.add(favFood);
        }
        return favFoodSet;
    }
}
