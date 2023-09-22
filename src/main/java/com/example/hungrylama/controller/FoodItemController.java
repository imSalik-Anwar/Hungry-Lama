package com.example.hungrylama.controller;

import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponseWithRestName;
import com.example.hungrylama.Enum.FoodCategory;
import com.example.hungrylama.exception.FoodItemNotFoundException;
import com.example.hungrylama.exception.RestaurantNotFoundException;
import com.example.hungrylama.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/food-item")
public class FoodItemController {

    final FoodItemService foodItemService;
    @Autowired
    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    // get all foods of a particular category
    @GetMapping("/get-food-from-particular-category")
    public ResponseEntity getFoodFromCategory(@RequestParam("category") FoodCategory category){
        try{
            List<FoodItemResponseWithRestName> response = foodItemService.getFoodFromCategory(category);
            return new ResponseEntity(response, HttpStatus.FOUND);
        } catch (FoodItemNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // get all MAIN_COURSE items with price above x rupees from a particular restaurant.

    // get all veg foods of a restaurant
    @GetMapping("/all-non-veg-from-a-restaurant")
    public ResponseEntity allNonVegFromARestaurant(@RequestParam("name") String name){
        try{
            List<FoodItemResponse> responses = foodItemService.allNonVegFromARestaurant(name);
            return new ResponseEntity(responses, HttpStatus.FOUND);
        } catch (FoodItemNotFoundException | RestaurantNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    // get all non veg foods of a restaurant

    // Get cheapest 5 food items of a partiuclar restaurant

    // Get costliest 5 food items of a partiuclar restaurant

    // Get costliest 5 food items of a partiuclar catgeory -> name fo dish and rest which serves that dish
}
