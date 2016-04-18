package com.github.leertaken.leertaak4.Opdracht1.Lijang2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
*   (Account synchronization) Rewrite Listing 29.9, ThreadCooperation.java,
*   using object’s wait() and notifyAll() methods.
*/
public class ThreadCooperation {
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
        @Override public void run() {
            try {// Purposely delay it to let the withdraw method proceed.
                while (true) {
                    account.deposit((int)(Math.random() * 10) + 1);
                    Thread.sleep(1000);
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    // A task for subtracting an amount from the account
    public static class WithdrawTask implements Runnable {
        @Override public void run() {
            while (true) {
                account.withdraw((int)(Math.random() * 10) + 1);
            }
        }
    }

    // An inner class for account
    private static class Account {
        // Create a new lock
        private Object lock = new Object();

        private int balance = 0;

        public int getBalance() {
            return balance;
        }

        public void withdraw(int amount) {
            synchronized (lock) {
                try {
                    while (balance < amount) {
                        System.out.println("\t\t\tWait for a deposit");
                        lock.wait();
                    }
                    balance -= amount;
                    System.out.println("\t\t\tWithdraw " + amount +
                        "\t\t" + getBalance());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void deposit(int amount) {
            synchronized (lock) {
                balance += amount;
                System.out.println("Deposit " + amount +
                        "\t\t\t\t\t" + getBalance());

                // Signal thread waiting on the condition
                lock.notifyAll(); // Release the lock
            }
        }
    }
}