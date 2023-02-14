package com.project.example.exceptions;

public class EmailExistException extends Exception{
    public EmailExistException(){
        super(ExMessages.EMAIL_EXIST);
    }

    public EmailExistException(String message){
        super(ExMessages.EMAIL_EXIST+"; "+message);
    }

}
