package com.github.leertaken.leertaak4.Opdracht1;

/**
 * Created by Sergen on 18-4-2016.
 */

import java.util.concurrent.atomic.AtomicInteger;

public class MainDecrement {
    static AtomicInteger next = new AtomicInteger(1);
    private Object lock = new Object();

    public static void main(String[] args){
        MainDecrement main = new MainDecrement();
        main.startThreads();
    }

    public void startThreads(){
        synchronized (lock) {
            for (int i = 0; i < 4; i++) {
                new Thread(new PrintIncrementThread()).start();
                next.set(next.get() + 1);
            }
            lock.notifyAll();
        }
    }

    public class PrintIncrementThread implements Runnable{
        public void run(){
            synchronized (lock) {
                int num = next.decrementAndGet();
                System.out.print(num);
                System.out.println(num);
                lock.notifyAll();
            }
        }
    }
}
