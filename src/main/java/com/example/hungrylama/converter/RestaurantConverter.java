package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.requestDTOs.RestaurantRequest;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.DTO.responseDTOs.RestaurantResponse;
import com.example.hungrylama.model.FoodItem;
import com.example.hungrylama.model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantConverter {

    public static Restaurant fromRestaurantRequestToRestaurant(RestaurantRequest restaurantRequest){
        return Restaurant.builder()
                .name(restaurantRequest.getName())
                .restaurantCategory(restaurantRequest.getCategory())
                .open(true)
                .location(restaurantRequest.getLocation())
                .zipCode(restaurantRequest.getZipCode())
                .contactNumber(restaurantRequest.getContactNumber())
                .menu(new ArrayList<>())
                .orders(new ArrayList<>())
                .build();
    }

    public static RestaurantResponse fromRestaurantToRestaurantResponse(Restaurant restaurant) {
        List<FoodItemResponse> menu = new ArrayList<>();
        for (FoodItem foodItem : restaurant.getMenu()) {
            FoodItemResponse response = FoodItemConverter.fromFoodItemToFoodItemResponse(foodItem);
            menu.add(response);
        }

        return RestaurantResponse.builder()
                .name(restaurant.getName())
                .contactNumber(restaurant.getContactNumber())
                .address(restaurant.getZipCode()+", "+restaurant.getLocation())
                .category(restaurant.getRestaurantCategory())
                .open(restaurant.isOpen())
                .menu(menu)
                .build();
    }
}
