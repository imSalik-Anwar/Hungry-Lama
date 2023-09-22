package com.example.hungrylama.DTO.responseDTOs;

import com.example.hungrylama.Enum.RestaurantCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponse {

    String name;

    String contactNumber;

    String address;

    RestaurantCategory category;

    boolean open;

    List<FoodItemResponse> menu;
}
