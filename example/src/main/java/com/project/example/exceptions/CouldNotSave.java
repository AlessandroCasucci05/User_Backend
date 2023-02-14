package com.project.example.exceptions;

public class CouldNotSave extends Exception{
    public CouldNotSave(){
        super(ExMessages.COULD_NOT_SAVE);
    }

    public CouldNotSave(String message){
        super(ExMessages.COULD_NOT_SAVE+"; "+message);
    }
}
