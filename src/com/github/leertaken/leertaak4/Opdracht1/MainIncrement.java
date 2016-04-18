package com.github.leertaken.leertaak4.Opdracht1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Sergen on 18-4-2016.
 */
public class MainIncrement {

    static AtomicInteger next = new AtomicInteger(0);
    private Object lock = new Object();

    public static void main(String[] args){
        MainIncrement main = new MainIncrement();
        main.startThreads();
    }

    public void startThreads(){
        for (int i = 0; i < 4; i++) {
            new Thread(new PrintIncrementThread()).start();
        }
    }

    public class PrintIncrementThread implements Runnable{
        public void run(){
            synchronized (lock) {
                int num = next.incrementAndGet();
                System.out.print(num);
                System.out.println(num);
                lock.notifyAll();
            }
        }
    }
}
