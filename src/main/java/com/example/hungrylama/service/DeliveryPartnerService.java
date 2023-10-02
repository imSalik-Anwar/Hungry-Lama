package com.example.hungrylama.service;

import com.example.hungrylama.DTO.requestDTOs.DeliveryPartnerRequest;
import com.example.hungrylama.DTO.responseDTOs.DeliveryPartnerResponse;
import com.example.hungrylama.converter.DeliverPartnerConverter;
import com.example.hungrylama.exception.DeliveryPartnerAlreadyExistsException;
import com.example.hungrylama.exception.DeliveryPartnerNotFoundException;
import com.example.hungrylama.exception.FoodItemNotFoundException;
import com.example.hungrylama.model.DeliveryPartner;
import com.example.hungrylama.repository.DeliveryPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryPartnerService {
    final DeliveryPartnerRepository deliveryPartnerRepository;
    @Autowired
    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    public DeliveryPartnerResponse addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        // check if delivery partner already exists
        Optional<DeliveryPartner> deliverPartnerOptional = deliveryPartnerRepository.findByContactNumber(deliveryPartnerRequest.getContact());
        if(deliverPartnerOptional.isPresent()){
            throw new DeliveryPartnerAlreadyExistsException("Deliver partner with this contact already exists.");
        }
        // create new delivery partner
        DeliveryPartner deliveryPartner = DeliverPartnerConverter.fromDeliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequest);
        DeliveryPartner savedDeliverPartner = deliveryPartnerRepository.save(deliveryPartner);
        return DeliverPartnerConverter.fromDeliverPartnerToDeliveryPartnerResponse(savedDeliverPartner);
    }

    public String updateContact(String oldContact, String newContact) {
        // if old and new contact are same
        if(oldContact.equals(newContact)){
            return "Old and New contacts can't be same.";
        }
        Optional<DeliveryPartner> deliveryPartnerOptional = deliveryPartnerRepository.findByContactNumber(oldContact);
        if(deliveryPartnerOptional.isEmpty()){
            throw new DeliveryPartnerNotFoundException("Delivery Partner does not exists.");
        }
        DeliveryPartner deliveryPartner = deliveryPartnerOptional.get();
        deliveryPartner.setContactNumber(newContact);
        deliveryPartnerRepository.save(deliveryPartner);
        return "Your contact has been changed from ["+oldContact+"] to ["+newContact+"]";
    }

    public DeliveryPartnerResponse seeAllOrders(String contact) {
        Optional<DeliveryPartner> deliveryPartnerOptional = deliveryPartnerRepository.findByContactNumber(contact);
        if(deliveryPartnerOptional.isEmpty()){
            throw new DeliveryPartnerNotFoundException("Delivery Partner does not exists.");
        }
        DeliveryPartner deliveryPartner = deliveryPartnerOptional.get();
        return DeliverPartnerConverter.fromDeliverPartnerToDeliveryPartnerResponse(deliveryPartner);
    }
}
