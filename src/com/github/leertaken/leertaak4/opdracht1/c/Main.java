package com.github.leertaken.leertaak4.opdracht1.c;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static int numberOfThreads = 4;

    public static void main(String[] args) {
        for (int i = numberOfThreads; i >= 1; i--) {
            new Thread(new NumberPrinter(i)).start();
        }
    }

    static class NumberPrinter implements Runnable {
        private static Lock lock = new ReentrantLock(true);
        private static Condition condition = lock.newCondition();

        private int lastNumber = Main.numberOfThreads;

        public NumberPrinter(int lastNumber) {
            this.lastNumber = lastNumber;
        }

        public void run() {
            System.out.println("newThread");
            lock.lock();

            try {
                while (lastNumber != Main.numberOfThreads) {
                    condition.await();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } finally {
                for (int i = 0; i < 2; i++) {
                    System.out.print(lastNumber);
                }
                System.out.print("\n");
                Main.numberOfThreads = lastNumber - 1;
            }
            condition.signalAll();
            lock.unlock();
            System.out.println("closethread");
        }
    }
}