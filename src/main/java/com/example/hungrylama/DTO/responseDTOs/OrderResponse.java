package com.example.hungrylama.DTO.responseDTOs;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    String orderId;

    Date orderTime;

    String restaurant;

    String customerName;

    String address;

    List<FoodItemResponseForOrder> dishes = new ArrayList<>();

    BillResponse bill;
}
