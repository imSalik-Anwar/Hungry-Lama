package com.example.hungrylama.exception;

public class DeliveryPartnerAlreadyExistsException extends RuntimeException{
    public DeliveryPartnerAlreadyExistsException(String message){
        super(message);
    }
    public DeliveryPartnerAlreadyExistsException(){}
}
