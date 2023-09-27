package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.requestDTOs.FoodReqWithRestId;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponseWithQuantity;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponseWithRestName;
import com.example.hungrylama.model.BasketItem;
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

    public static FoodItemResponse fromBasketItemToFoodItemResponse(BasketItem basketItem){
        return FoodItemResponse.builder()
                .dishName(basketItem.getDishName())
                .price(basketItem.getPrice())
                .category(basketItem.getCategory())
                .available(basketItem.isAvailable())
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

    public static FoodItemResponse fromFoodItemToFoodItemResponse(FoodItem foodItem) {
        return FoodItemResponse.builder()
                .available(foodItem.isAvailable())
                .category(foodItem.getCategory())
                .price(foodItem.getPrice())
                .dishName(foodItem.getDishName())
                .build();
    }

    public static BasketItem fromFoodItemToBasketItem(FoodItem foodItem) {
        return BasketItem.builder()
                .dishName(foodItem.getDishName())
                .price(foodItem.getPrice())
                .category(foodItem.getCategory())
                .veg(foodItem.isVeg())
                .available(foodItem.isAvailable())
                .build();
    }

    public static FoodItemResponseWithQuantity fromBasketItemToFoodItemResponseWithQuantity(BasketItem basketItem) {
        return FoodItemResponseWithQuantity.builder()
                .quantity(basketItem.getQuantity())
                .dishName(basketItem.getDishName())
                .price(basketItem.getPrice())
                .category(basketItem.getCategory())
                .available(basketItem.isAvailable())
                .build();
    }
}
