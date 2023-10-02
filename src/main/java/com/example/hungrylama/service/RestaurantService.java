package com.example.hungrylama.service;

import com.example.hungrylama.DTO.requestDTOs.FoodReqWithRestId;
import com.example.hungrylama.DTO.requestDTOs.RestaurantRequest;
import com.example.hungrylama.DTO.responseDTOs.RestaurantResponse;
import com.example.hungrylama.converter.FoodItemConverter;
import com.example.hungrylama.converter.RestaurantConverter;
import com.example.hungrylama.exception.FoodItemNotFoundException;
import com.example.hungrylama.exception.RestaurantNotFoundException;
import com.example.hungrylama.model.FoodItem;
import com.example.hungrylama.model.Restaurant;
import com.example.hungrylama.repository.FoodItemRepository;
import com.example.hungrylama.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    final RestaurantRepository restaurantRepository;
    final FoodItemRepository foodItemRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, FoodItemRepository foodItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
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

    public String changeDishAvailableStatus(String restaurant, String dish) {
        // check if restaurant is valid
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByName(restaurant);
        if(restaurantOptional.isEmpty()){
            throw new RestaurantNotFoundException("Restaurant does not exists.");
        }
        Restaurant rest = restaurantOptional.get();
        List<FoodItem> foodItemList = rest.getMenu();
        for(FoodItem item : foodItemList){
            if(item.getDishName().equals(dish)){
                item.setAvailable(!item.isAvailable());
                restaurantRepository.save(rest);
                if(item.isAvailable()){
                    return "Dish availability status has been changed to: Available";
                } else {
                    return "Dish availability status has been changed to: Not-available";
                }
            }
        }
        throw new FoodItemNotFoundException("Dish does not exists.");
    }
}
