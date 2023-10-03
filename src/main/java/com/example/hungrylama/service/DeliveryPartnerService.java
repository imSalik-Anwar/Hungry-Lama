package com.example.hungrylama.service;

import com.example.hungrylama.DTO.requestDTOs.DeliveryPartnerRequest;
import com.example.hungrylama.DTO.responseDTOs.DeliveryPartnerResponse;
import com.example.hungrylama.converter.DeliverPartnerConverter;
import com.example.hungrylama.exception.DeliveryPartnerAlreadyExistsException;
import com.example.hungrylama.exception.DeliveryPartnerNotFoundException;
import com.example.hungrylama.model.DeliveryPartner;
import com.example.hungrylama.repository.DeliveryPartnerRepository;
import com.example.hungrylama.utility.MailComposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryPartnerService {
    final DeliveryPartnerRepository deliveryPartnerRepository;
    final JavaMailSender javaMailSender;
    @Autowired
    public DeliveryPartnerService(DeliveryPartnerRepository deliveryPartnerRepository, JavaMailSender javaMailSender) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
        this.javaMailSender = javaMailSender;
    }

    public DeliveryPartnerResponse addDeliveryPartner(DeliveryPartnerRequest deliveryPartnerRequest) {
        // check if delivery partner already exists
        Optional<DeliveryPartner> deliverPartnerOptional = deliveryPartnerRepository.findByContactNumberOrEmail(deliveryPartnerRequest.getContact(),
                deliveryPartnerRequest.getEmail());
        if(deliverPartnerOptional.isPresent()){
            throw new DeliveryPartnerAlreadyExistsException("Deliver partner with this contact already exists.");
        }
        // create new delivery partner
        DeliveryPartner deliveryPartner = DeliverPartnerConverter.fromDeliveryPartnerRequestToDeliveryPartner(deliveryPartnerRequest);
        DeliveryPartner savedDeliverPartner = deliveryPartnerRepository.save(deliveryPartner);
        // send email
        //SimpleMailMessage message = MailComposer.composeDeliverPartnerOnboardingMail(deliveryPartner);
        //javaMailSender.send(message);
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
