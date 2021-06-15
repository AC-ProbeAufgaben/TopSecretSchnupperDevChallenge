package com.amiconsult.topsecretschnupperdevchallenge.model;

import org.mapstruct.Mapper;

@Mapper
public interface FavFoodMapper {
    FavFoodDto toDto(FavFood favFood);
}
