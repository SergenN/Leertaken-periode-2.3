package com.github.leertaken.leertaak4.opdracht1.b;

/**
 * Created by Sergen on 17-3-2016.
 */

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
public class Main {
    private static Account account = new Account();

    public static void main(String[] args) {
        // Create a thread pool with two threads
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new DepositTask());
        executor.execute(new WithdrawTask());
        executor.shutdown();

        System.out.println("Thread 1\t\tThread 2\t\tBalance");
    }

    // A task for adding an amount to the account
    public static class DepositTask implements Runnable {
        public void run() {
            try {
                while (true) {
                    account.deposit((int) (Math.random() * 10) + 1);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    // A task for subtracting an amount from the account
    public static class WithdrawTask implements Runnable {
        public void run() {
            while (true) {
                try {
                    account.withdraw((int) (Math.random() * 10) + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // An inner class for account
    private static class Account {
        // Create a new lock
        private static Lock lock = new ReentrantLock();

        // Create a condition
        private static Condition newDeposit = lock.newCondition();

        private int balance = 0;

        public int getBalance() {
            return balance;
        }

        public synchronized void withdraw(int amount) throws InterruptedException {
            while (balance < amount) {
                System.out.println("\t\t\tWait for a deposit");
                wait();
            }
            balance -= amount;
            System.out.println("\t\t\tWithdraw " + amount + "\t\t" + getBalance());
            notify();
        }

        public synchronized void deposit(int amount) throws InterruptedException {
            synchronized (this) {
                balance += amount;
                System.out.println("Deposit " + amount + "\t\t\t\t\t" + getBalance());

                notifyAll();
            }
        }
    }
}