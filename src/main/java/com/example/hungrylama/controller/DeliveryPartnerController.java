package com.example.hungrylama.controller;

import com.example.hungrylama.DTO.requestDTOs.DeliveryPartnerRequest;
import com.example.hungrylama.DTO.responseDTOs.DeliveryPartnerResponse;
import com.example.hungrylama.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-partner")
public class DeliveryPartnerController {
    final DeliveryPartnerService deliveryPartnerService;
    @Autowired
    public DeliveryPartnerController(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    @PostMapping("/add")
    public ResponseEntity addDeliveryPartner(@RequestBody DeliveryPartnerRequest deliveryPartnerRequest){
        try{
            DeliveryPartnerResponse response = deliveryPartnerService.addDeliveryPartner(deliveryPartnerRequest);
            return new ResponseEntity(response, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/old-contact/{oldContact}/new-contact/{newContact}")
    public ResponseEntity<String> updateContact(@PathVariable("oldContact") String oldContact, @PathVariable("newContact") String newContact){
        try{
            String response = deliveryPartnerService.updateContact(oldContact, newContact);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
