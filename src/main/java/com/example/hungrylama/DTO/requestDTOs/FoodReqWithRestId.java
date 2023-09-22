package com.example.hungrylama.DTO.requestDTOs;

import com.example.hungrylama.Enum.FoodCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodReqWithRestId {

    int restaurantId;

    String dishName;

    double price;

    FoodCategory category;

    boolean veg;
}
