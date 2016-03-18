package com.github.leertaken.leertaak4.opdracht1.a;

/**
 * Created by Sergen on 17-3-2016.
 */
public class IncrementThread implements Runnable {

    private Sum sum;

    public IncrementThread(Sum sum){
        this.sum = sum;
    }

    public void run(){
        sum.increment();
    }

}
