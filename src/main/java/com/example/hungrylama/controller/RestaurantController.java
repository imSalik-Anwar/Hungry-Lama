package com.example.hungrylama.controller;

import com.example.hungrylama.DTO.requestDTOs.FoodReqWithRestId;
import com.example.hungrylama.DTO.requestDTOs.RestaurantRequest;
import com.example.hungrylama.DTO.responseDTOs.RestaurantResponse;
import com.example.hungrylama.exception.RestaurantNotFoundException;
import com.example.hungrylama.model.FoodItem;
import com.example.hungrylama.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    final RestaurantService restaurantService;
    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add-restaurant")
    public ResponseEntity addRestaurant(@RequestBody RestaurantRequest restaurantRequest){
        try{
            RestaurantResponse response = restaurantService.addRestaurant(restaurantRequest);
            return new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity("Input is invalid! Double check your contact and restaurant name.", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update-open-status")
    public ResponseEntity<String> updateOpenStatus(@RequestParam("id") int id){
        try{
            String response = restaurantService.updateOpenStatus(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RestaurantNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-food-to-menu")
    public ResponseEntity addFoodItemToMenu(@RequestBody FoodReqWithRestId foodReqWithRestId){
        try{
            RestaurantResponse response = restaurantService.addFoodItemToMenu(foodReqWithRestId);
            return new ResponseEntity(response, HttpStatus.CREATED);
        } catch (RestaurantNotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-dish-available-status")
    public ResponseEntity<String> changeDishAvailableStatus(@RequestParam("restaurant") String restaurant, @RequestParam("dish") String dish){
        try{
            String response = restaurantService.changeDishAvailableStatus(restaurant, dish);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
