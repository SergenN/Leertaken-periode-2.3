package com.github.leertaken.leertaak4.Opdracht1.Lijang1;

/**
 * Created by Sergen on 21-3-2016.
 */
public class NummerSync implements Nummer{
    private int num = 0;

    public synchronized void increment(){
        num++;
        print();
    }

    private void print(){
        System.out.println(num);
    }
}
