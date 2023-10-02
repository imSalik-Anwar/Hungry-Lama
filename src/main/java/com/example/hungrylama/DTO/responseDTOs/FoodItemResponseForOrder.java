package com.example.hungrylama.DTO.responseDTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemResponseForOrder {

    String dishName;

    double price;

    int quantity;

}
