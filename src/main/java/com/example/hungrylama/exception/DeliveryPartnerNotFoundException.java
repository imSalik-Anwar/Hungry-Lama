package com.example.hungrylama.exception;

public class DeliveryPartnerNotFoundException extends RuntimeException{
    public DeliveryPartnerNotFoundException(String message){
        super(message);
    }
    public DeliveryPartnerNotFoundException(){}
}
