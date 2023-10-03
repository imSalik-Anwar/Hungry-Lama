package com.example.hungrylama.DTO.responseDTOs;

import com.example.hungrylama.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerResponse {

    int id;

    String name;

    String contact;

    Gender gender;

    String email;

    List<OrderResponseForDeliveryPartner> orders = new ArrayList<>();
}
