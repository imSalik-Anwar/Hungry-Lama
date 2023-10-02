package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.responseDTOs.BillResponse;
import com.example.hungrylama.DTO.responseDTOs.FoodItemResponseForOrder;
import com.example.hungrylama.DTO.responseDTOs.OrderResponse;
import com.example.hungrylama.model.*;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {

    public static OrderResponse fromOrderDetailsToOrderResponse(OrderEntity order, Bill bill, Restaurant restaurant, Customer customer){
        List<FoodItemResponseForOrder> list = new ArrayList<>();
        for(BasketItem item : order.getDishes()){
            FoodItemResponseForOrder dish = new FoodItemResponseForOrder();
            dish.setDishName(item.getDishName());
            dish.setQuantity(item.getQuantity());
            dish.setPrice(item.getPrice());
            list.add(dish);
        }

        BillResponse billResponse = new BillResponse();
        billResponse.setOrderValue(bill.getOrderValue());
        billResponse.setGst(bill.getGst());
        billResponse.setCoupon(bill.getCoupon());
        billResponse.setBillAmount(bill.getBillAmount());
        billResponse.setDiscount(bill.getDiscount());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderTime(order.getOrderTime())
                .restaurant(restaurant.getName())
                .customerName(customer.getName())
                .address(customer.getZipCode()+", "+customer.getCity())
                .dishes(list)
                .bill(billResponse)
                .build();
    }
}
