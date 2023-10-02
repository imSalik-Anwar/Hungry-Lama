package com.example.hungrylama.DTO.responseDTOs;

import com.example.hungrylama.Enum.Coupon;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {

    double orderValue;

    double gst;

    Coupon coupon;

    double discount;

    double billAmount;
}
