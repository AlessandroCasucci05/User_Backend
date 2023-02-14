package com.project.example.exceptions;

public class NoUserFoundException extends Exception{
    public NoUserFoundException(){
        super(ExMessages.NO_USER_FOUND);
    }

    public NoUserFoundException(String message){
        super(ExMessages.NO_USER_FOUND+" ; "+message);
    }
}
