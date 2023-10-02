package com.example.hungrylama.DTO.requestDTOs;

import com.example.hungrylama.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPartnerRequest {

    String name;

    String contact;

    Gender gender;
}
