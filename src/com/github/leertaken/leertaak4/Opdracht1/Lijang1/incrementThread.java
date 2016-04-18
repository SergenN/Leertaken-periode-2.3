package com.github.leertaken.leertaak4.Opdracht1.Lijang1;

/**
 * Created by Sergen on 21-3-2016.
 */
public class IncrementThread implements Runnable{

    private Nummer num;

    public IncrementThread(Nummer nummer){
        this.num = nummer;
    }

    public void run(){
        num.increment();
    }
}
