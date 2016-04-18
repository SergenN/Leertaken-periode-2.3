package com.github.leertaken.leertaak4.opdracht1.a;

/**
 * Created by Sergen on 17-3-2016.
 */
public class Main {

    public static void main(String[] args) {
        Sum sum = new Sum();
        for (int i = 0; i < 1000; i++) {
            (new Thread(new IncrementThread(sum))).start();
        }
        System.out.println(sum.toString());
    }

}
