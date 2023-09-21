package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.requestDTOs.CustomerRequest;
import com.example.hungrylama.DTO.responseDTOs.BasketResponse;
import com.example.hungrylama.DTO.responseDTOs.CustomerResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.model.Basket;
import com.example.hungrylama.model.Customer;
import com.example.hungrylama.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class CustomerConverter {

    public static Customer fromCustomerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .name(customerRequest.getName())
                .email(customerRequest.getEmail())
                .password(customerRequest.getPassword())
                .zipCode(customerRequest.getZipCode())
                .city(customerRequest.getCity())
                .gender(customerRequest.getGender())
                .build();
    }
    public static CustomerResponse fromCustomerToCustomerResponse(Customer customer){
        // get customer's basket
        Basket basket = customer.getBasket();
        // get his food item list and convert it to food response list for adding into customer response DTO
        List<FoodItem> list = basket.getFoodItems();
        List<FoodItemResponse> list2 = new ArrayList<>();
        for(FoodItem item : list){
            FoodItemResponse response = FoodItemResponse.builder()
                    .dishName(item.getDishName())
                    .price(item.getPrice())
                    .category(item.getCategory())
                    .build();
            list2.add(response);
        }
        // now prepare the basket response
        BasketResponse basketResponse = BasketResponse.builder()
                .basketValue(basket.getBasketValue())
                .foodItems(list2)
                .build();
        // set all the values to response customer
        return CustomerResponse.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getZipCode() + " - " + customer.getCity())
                .basketResponse(basketResponse)
                .build();
    }
}
