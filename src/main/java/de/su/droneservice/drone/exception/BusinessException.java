package de.su.droneservice.drone.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private String message;

    public BusinessException(String message){
        super(message);
        this.message = message;
    }

    public BusinessException(String message, Throwable e){
        super(message, e);
        this.message = message;
    }
}
