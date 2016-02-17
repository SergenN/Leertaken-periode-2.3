package com.github.leertaken.leertaak1.opdracht19.multiformat;

/**
 * Created by Hp user on 17-2-2016.
 */
public class NumberBaseException extends Exception {
    public NumberBaseException(Base base){
        System.err.println("Wrong character used. ");
        System.err.println("Please use: "+base.getDigits());
    }
}
