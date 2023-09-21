package com.example.hungrylama.DTO.requestDTOs;

import com.example.hungrylama.Enum.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    String name;

    String email;

    String password;

    Gender gender;

    int zipCode;

    String city;
}
