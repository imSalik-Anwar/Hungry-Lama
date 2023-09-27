package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.responseDTOs.BasketResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponseWithQuantity;
import com.example.hungrylama.model.Basket;
import com.example.hungrylama.model.BasketItem;

import java.util.ArrayList;
import java.util.List;

public class BasketConverter {

    public static BasketResponse fromBasketToBasketResponse(Basket basket){
        List<FoodItemResponseWithQuantity> menu = new ArrayList<>();
        for(BasketItem basketItem : basket.getBasketItemList()){
            menu.add(FoodItemConverter.fromBasketItemToFoodItemResponseWithQuantity(basketItem));
        }
        return BasketResponse.builder()
                .basketValue(basket.getBasketValue())
                .foodItems(menu)
                .build();
    }
}
