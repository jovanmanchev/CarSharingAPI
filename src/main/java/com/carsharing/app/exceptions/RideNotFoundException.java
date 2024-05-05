package com.carsharing.app.exceptions;

public class RideNotFoundException extends RuntimeException{

    public RideNotFoundException(String message){
        super(message);
    }
}
