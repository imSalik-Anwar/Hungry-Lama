package com.example.hungrylama.DTO.responseDTOs;

import com.example.hungrylama.Enum.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemResponseWithRestName{
    String dishName;

    double price;

    boolean available;

    FoodCategory category;

    String restaurantName;
}
