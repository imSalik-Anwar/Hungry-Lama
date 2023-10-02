package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.requestDTOs.DeliveryPartnerRequest;
import com.example.hungrylama.DTO.responseDTOs.DeliveryPartnerResponse;
import com.example.hungrylama.model.DeliveryPartner;

import java.util.ArrayList;

public class DeliverPartnerConverter {

    public static DeliveryPartner fromDeliveryPartnerRequestToDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest){
        return DeliveryPartner.builder()
                .name(deliveryPartnerRequest.getName())
                .contactNumber(deliveryPartnerRequest.getContact())
                .gender(deliveryPartnerRequest.getGender())
                .orders(new ArrayList<>())
                .build();
    }

    public static DeliveryPartnerResponse fromDeliverPartnerToDeliveryPartnerResponse(DeliveryPartner deliveryPartner){
        return DeliveryPartnerResponse.builder()
                .id(deliveryPartner.getId())
                .name(deliveryPartner.getName())
                .contact(deliveryPartner.getContactNumber())
                .gender(deliveryPartner.getGender())
                .build();
    }
}
