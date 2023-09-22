package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.requestDTOs.FoodReqWithRestId;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponseWithRestName;
import com.example.hungrylama.model.FoodItem;

public class FoodItemConverter {

    public static FoodItem fromFoodItemReqWithRestIdToFoodItem(FoodReqWithRestId foodReqWithRestId){
        return FoodItem.builder()
                .dishName(foodReqWithRestId.getDishName())
                .veg(foodReqWithRestId.isVeg())
                .price(foodReqWithRestId.getPrice())
                .category(foodReqWithRestId.getCategory())
                .available(true)
                .build();
    }

    public static FoodItemResponse fromFoodItemToFoodItemResponse(FoodItem foodItem){
        return FoodItemResponse.builder()
                .dishName(foodItem.getDishName())
                .price(foodItem.getPrice())
                .category(foodItem.getCategory())
                .available(foodItem.isAvailable())
                .build();
    }
    public static FoodItemResponseWithRestName fromFoodItemToFoodItemResponseWithRestName(FoodItem foodItem){
        return FoodItemResponseWithRestName.builder()
                .dishName(foodItem.getDishName())
                .price(foodItem.getPrice())
                .category(foodItem.getCategory())
                .available(foodItem.isAvailable())
                .restaurantName(foodItem.getRestaurant().getName())
                .build();
    }
}
