package com.example.hungrylama.DTO.requestDTOs;

import com.example.hungrylama.DTO.responseDTOs.FoodItemResponse;
import com.example.hungrylama.Enum.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {

    String name;

    String contactNumber;

    String location;

    int zipCode;

    RestaurantCategory category;
}
