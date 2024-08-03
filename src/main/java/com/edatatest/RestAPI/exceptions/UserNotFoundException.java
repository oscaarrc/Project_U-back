package com.edatatest.RestAPI.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User not found.");
    }
}
