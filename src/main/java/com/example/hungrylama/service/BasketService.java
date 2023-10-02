package com.example.hungrylama.service;

import com.example.hungrylama.DTO.responseDTOs.BasketResponse;
import com.example.hungrylama.DTO.responseDTOs.CustomerResponse;
import com.example.hungrylama.converter.BasketConverter;
import com.example.hungrylama.converter.CustomerConverter;
import com.example.hungrylama.converter.FoodItemConverter;
import com.example.hungrylama.exception.CustomerNotFoundException;
import com.example.hungrylama.exception.EmptyBasketException;
import com.example.hungrylama.exception.FoodItemNotFoundException;
import com.example.hungrylama.exception.RestaurantNotFoundException;
import com.example.hungrylama.model.*;
import com.example.hungrylama.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {
    final BasketRepository basketRepository;
    final CustomerRepository customerRepository;
    final RestaurantRepository restaurantRepository;
    final FoodItemRepository foodItemRepository;
    final BasketItemRepository basketItemRepository;
    @Autowired
    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository,
                         RestaurantRepository restaurantRepository, FoodItemRepository foodItemRepository,
                         BasketItemRepository basketItemRepository) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
        this.basketItemRepository = basketItemRepository;
    }

    public CustomerResponse addDishToBasket(String email, String password, String dishName, String restaurantName, int quantity) {
        // validate and get the customer
        Optional<Customer> customerOptional = customerRepository.findByEmailAndPassword(email, password);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Invalid input.");
        }
        // check if restaurant is valid and open
        Optional<Restaurant> restaurantOptional = restaurantRepository.findByName(restaurantName);
        if(restaurantOptional.isEmpty()){
            throw  new RestaurantNotFoundException("Restaurant does not exists.");
        }
        if(!restaurantOptional.get().isOpen()){
            throw  new RestaurantNotFoundException("Alas! Restaurant is closed. Hang on tight. We will be back soon.");
        }
        // check if dish exists in DB and restaurant's menu and it's available
        Optional<FoodItem> foodItemOptional = foodItemRepository.findByDishName(dishName);
        if(foodItemOptional.isEmpty()){
            throw  new FoodItemNotFoundException("The dish you requested does not exists on Hungry Llama.");
        }
        FoodItem requestedFoodItem = null;
        for(FoodItem foodItem : restaurantOptional.get().getMenu()){
            if(foodItem.equals(foodItemOptional.get()) && foodItem.isAvailable()){
                requestedFoodItem = foodItem;
                break;
            }
        }
        if(requestedFoodItem == null){
            throw  new FoodItemNotFoundException("The dish either doesn't exists in "+restaurantName+"'s menu or is currently unavailable.");
        }
        // check if basket already has item from other restaurant
        Restaurant requestedRestaurant = restaurantOptional.get();
        if(!customerOptional.get().getBasket().getBasketItemList().isEmpty()){
            Restaurant curRestaurant = customerOptional.get().getBasket().getBasketItemList().get(0).getFoodItem().getRestaurant();
            if(!requestedRestaurant.equals(curRestaurant)){
                throw new RuntimeException("You basket already has dishes from other restaurant. Please clear your basket before adding dishes from this restaurant.");
            }
        }
        // if all checks are okay then get all the entities
        FoodItem foodItem = foodItemOptional.get();
        Customer customer = customerOptional.get();
        Basket basket = customer.getBasket();
        // now add the food item to the basket ========================================================
        // check if requested dish already exists in basket or not
        boolean alredayExist = false;
        BasketItem existing = null;
        if(!basket.getBasketItemList().isEmpty()){
            for(BasketItem basketItem : basket.getBasketItemList()){
                if(basketItem.getDishName().equals(foodItem.getDishName())){
                    existing = basketItem;
                    alredayExist = true;
                    break;
                }
            }
        }
        if(!alredayExist){
            // convert food item to basket item and add basket item to basket
            BasketItem basketItem = FoodItemConverter.fromFoodItemToBasketItem(foodItem);
            // check if basket already has requested food item
            basketItem.setQuantity(quantity);
            basketItem.setBasket(basket);
            basketItem.setFoodItem(foodItem);
            basket.getBasketItemList().add(basketItem);
            basket.setBasketValue(basket.getBasketValue() + basketItem.getPrice() * quantity);
        } else {
            existing.setQuantity(existing.getQuantity() + quantity);
            basket.setBasketValue(basket.getBasketValue() + quantity * existing.getPrice());
        }

        // save basket to save basket item in it. Also, save customer.
        Basket savedBasket = basketRepository.save(basket);
        Customer savedCustomer = customerRepository.save(customer);
        // prepare response
        return CustomerConverter.fromCustomerToCustomerResponse(savedCustomer);
    }

    public BasketResponse seeBasket(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if (customerOptional.isEmpty()){
            throw  new CustomerNotFoundException("Customer with this email does not exists.");
        }
        Customer customer = customerOptional.get();
        Basket basket = customer.getBasket();
        if(basket.getBasketItemList().isEmpty()){
            throw new EmptyBasketException("You basket has no dishes. Add some");
        }
        // prepare basket response
        return BasketConverter.fromBasketToBasketResponse(basket);
    }

    public void clearBasket(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if(customerOptional.isEmpty()){
            throw new CustomerNotFoundException("Invalid input.");
        }
        Customer customer = customerOptional.get();
        if(customer.getBasket().getBasketItemList().isEmpty()){
            throw new EmptyBasketException("Your basket is already clean.");
        }
        // clear the basket
        Basket basket = customer.getBasket();
        List<BasketItem> basketItemList = basket.getBasketItemList();
        for(BasketItem basketItem : basketItemList){
            basketItem.setFoodItem(null);
            basketItem.setBasket(null);
            basketItemRepository.delete(basketItem);
        }
        basket.getBasketItemList().clear();
        basket.setBasketValue(0);
        basketRepository.save(customer.getBasket());
        customerRepository.save(customer);
    }
}
