package com.example.bisneslogic.util;

public class CardNotValidException extends RuntimeException{
    public CardNotValidException(String msg){
        super(msg);
    }
}
