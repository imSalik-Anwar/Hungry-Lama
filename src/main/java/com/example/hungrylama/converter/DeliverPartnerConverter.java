package com.example.hungrylama.converter;

import com.example.hungrylama.DTO.requestDTOs.DeliveryPartnerRequest;
import com.example.hungrylama.DTO.responseDTOs.DeliveryPartnerResponse;
import com.example.hungrylama.DTO.responseDTOs.OrderResponseForDeliveryPartner;
import com.example.hungrylama.model.DeliveryPartner;
import com.example.hungrylama.model.OrderEntity;

import java.util.ArrayList;
import java.util.List;

public class DeliverPartnerConverter {

    public static DeliveryPartner fromDeliveryPartnerRequestToDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest){
        return DeliveryPartner.builder()
                .name(deliveryPartnerRequest.getName())
                .contactNumber(deliveryPartnerRequest.getContact())
                .gender(deliveryPartnerRequest.getGender())
                .email(deliveryPartnerRequest.getEmail())
                .orders(new ArrayList<>())
                .build();
    }

    public static DeliveryPartnerResponse fromDeliverPartnerToDeliveryPartnerResponse(DeliveryPartner deliveryPartner){
        List<OrderResponseForDeliveryPartner> list = new ArrayList<>();
        for(OrderEntity order : deliveryPartner.getOrders()){
            OrderResponseForDeliveryPartner obj = new OrderResponseForDeliveryPartner();
            obj.setCustomer(order.getCustomer().getName());
            obj.setAddress(order.getCustomer().getZipCode()+", "+order.getCustomer().getCity());
            obj.setOrderId(order.getOrderId());
            obj.setOrderTime(order.getOrderTime());
            obj.setOrderValue(order.getBill().getBillAmount());

            list.add(obj);
        }

        return DeliveryPartnerResponse.builder()
                .id(deliveryPartner.getId())
                .name(deliveryPartner.getName())
                .contact(deliveryPartner.getContactNumber())
                .gender(deliveryPartner.getGender())
                .email(deliveryPartner.getEmail())
                .orders(list)
                .build();
    }
}
