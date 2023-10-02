package com.example.hungrylama.DTO.responseDTOs;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseForDeliveryPartner {

    String customer;

    String address;

    String orderId;

    Date orderTime;

    double orderValue;
}
