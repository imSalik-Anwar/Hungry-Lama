package com.example.hungrylama.exception;

public class EmptyBasketException extends RuntimeException{
    public EmptyBasketException(String message){
        super(message);
    }
    public EmptyBasketException(){}
}
