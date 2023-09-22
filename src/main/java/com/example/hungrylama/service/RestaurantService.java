package com.example.hungrylama.service;

import com.example.hungrylama.DTO.requestDTOs.FoodReqWithRestId;
import com.example.hungrylama.DTO.requestDTOs.RestaurantRequest;
import com.example.hungrylama.DTO.responseDTOs.RestaurantResponse;
import com.example.hungrylama.converter.FoodItemConverter;
import com.example.hungrylama.converter.RestaurantConverter;
import com.example.hungrylama.exception.RestaurantNotFoundException;
import com.example.hungrylama.model.FoodItem;
import com.example.hungrylama.model.Restaurant;
import com.example.hungrylama.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {
    final RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantResponse addRestaurant(RestaurantRequest restaurantRequest) throws Exception{
        // convert DTO to Model
        Restaurant restaurant = RestaurantConverter.fromRestaurantRequestToRestaurant(restaurantRequest);
        // save restaurant
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        // convert model to DTO
        return RestaurantConverter.fromRestaurantToRestaurantResponse(savedRestaurant);
    }

    public String updateOpenStatus(int id) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);
        if(restaurantOptional.isEmpty()){
            throw new RestaurantNotFoundException("Invalid restaurant id.");
        }
        Restaurant restaurant = restaurantOptional.get();
        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);

        if(restaurant.isOpen()){
            return "Status has been changed to open.";
        }
        return "Status has been changed to closed.";
    }

    public RestaurantResponse addFoodItemToMenu(FoodReqWithRestId foodReqWithRestId) {
        // find restaurant
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(foodReqWithRestId.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            throw new RestaurantNotFoundException("Invalid restaurant id.");
        }
        Restaurant restaurant = restaurantOptional.get();
        // convert DTO to food item model
        FoodItem foodItem = FoodItemConverter.fromFoodItemReqWithRestIdToFoodItem(foodReqWithRestId);
        foodItem.setRestaurant(restaurant);
        restaurant.getMenu().add(foodItem);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return RestaurantConverter.fromRestaurantToRestaurantResponse(savedRestaurant);
    }
}
