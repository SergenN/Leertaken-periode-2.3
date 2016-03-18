package com.github.leertaken.leertaak4.opdracht1.a;

/**
 * Created by Sergen on 17-3-2016.
 */
public class Sum {

    private int sum = 0;

    @Override
    public String toString(){
        return sum+"";
    }

    public void increment(){
        sum = sum+1;
    }
}
