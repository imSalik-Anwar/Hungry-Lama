package com.example.hungrylama.service;

import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponseWithRestName;
import com.example.hungrylama.Enum.FoodCategory;
import com.example.hungrylama.converter.FoodItemConverter;
import com.example.hungrylama.exception.FoodItemNotFoundException;
import com.example.hungrylama.exception.RestaurantNotFoundException;
import com.example.hungrylama.model.FoodItem;
import com.example.hungrylama.model.Restaurant;
import com.example.hungrylama.repository.FoodItemRepository;
import com.example.hungrylama.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    final FoodItemRepository foodItemRepository;
    final RestaurantRepository restaurantRepository;
    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository, RestaurantRepository restaurantRepository) {
        this.foodItemRepository = foodItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<FoodItemResponseWithRestName> getFoodFromCategory(FoodCategory category) {
        List<FoodItem> list = foodItemRepository.findByCategory(category);
        if(list.isEmpty()){
            throw new FoodItemNotFoundException("No "+category+" food found.");
        }
        List<FoodItemResponseWithRestName> responses = new ArrayList<>();
        for(FoodItem foodItem : list){
            FoodItemResponseWithRestName foodItemResponse = FoodItemConverter.fromFoodItemToFoodItemResponseWithRestName(foodItem);
            responses.add(foodItemResponse);
        }
        return responses;
    }

    public List<FoodItemResponse> allNonVegFromARestaurant(String name) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByName(name);
        if(restaurantOptional.isEmpty()){
            throw new RestaurantNotFoundException("Restaurant with name "+name+" does not exists.");
        }
        Restaurant restaurant = restaurantOptional.get();
        List<FoodItem> list = foodItemRepository.findAll();
        if(list.isEmpty()){
            throw new FoodItemNotFoundException("There is no veg food in "+name+"'s menu.");
        }
        List<FoodItemResponse> responses = new ArrayList<>();
        for (FoodItem foodItem : list){
            if(foodItem.isVeg() && restaurant.getName().equals(name)) {
                FoodItemResponse foodItemResponse = FoodItemConverter.fromFoodItemToFoodItemResponse(foodItem);
                responses.add(foodItemResponse);
            }
        }
        return  responses;
    }
}
