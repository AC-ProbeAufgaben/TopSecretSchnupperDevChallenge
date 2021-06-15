package com.amiconsult.topsecretschnupperdevchallenge.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper
public interface FoodFriendsMapper {

    FoodFriendsMapper INSTANCE = Mappers.getMapper( FoodFriendsMapper.class );

    @Mapping(source = "favFoods", target = "favorites", qualifiedByName = "getFoods")
    FoodFriendsDto toDto(FoodFriends foodFriend);

    FoodFriends fromDto(FoodFriendsDto foodFriendDto);

    @Named("getFoods")
    default List<String> getFavFoodsList(Set<FavFood> favFoods) {
        List<String> favFoodsList = new ArrayList<>();
        for (FavFood favFood : favFoods) {
            favFoodsList.add(favFood.getName());
        }
        return favFoodsList;
    }

}
