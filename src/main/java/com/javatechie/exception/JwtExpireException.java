package com.javatechie.exception;

public class JwtExpireException extends RuntimeException{

    public JwtExpireException (String msg){
        super(msg);
    }

}
