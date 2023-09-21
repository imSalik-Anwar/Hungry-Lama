package com.example.hungrylama.DTO.responseDTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
import java.util.ArrayList;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketResponse {

    double basketValue;

    List<FoodItemResponse> foodItems = new ArrayList<>();
}
