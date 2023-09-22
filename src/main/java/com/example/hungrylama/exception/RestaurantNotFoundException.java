package com.example.hungrylama.exception;

public class RestaurantNotFoundException extends RuntimeException{
    public RestaurantNotFoundException(String message){
        super(message);
    }
}
