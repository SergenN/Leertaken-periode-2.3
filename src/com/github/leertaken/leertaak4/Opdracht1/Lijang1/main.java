package com.github.leertaken.leertaak4.Opdracht1.Lijang1;

/**
 * Created by Sergen on 21-3-2016.
 */

/*
*
*   (Synchronizing threads) Write a program that launches 1000 threads. Each
*   thread adds 1 to a variable sum that initially is 0. You need to pass sum by reference
*   to each thread. In order to pass it by reference, define an Integer
*   wrapper object to hold sum. Run the program with and without synchronization
*   to see its effect.
*/
public class Main {
    public static void main(String[] args){
        Main main = new Main();
        main.launchUnsynchronizedSimulation();
        main.launchSynchronizedSimulation();
    }

    public void launchUnsynchronizedSimulation(){
        Nummer num = new NummerUnsync();
        for (int i = 0; i < 1000; i++){
            new Thread(new IncrementThread(num)).start();
        }
    }

    public void launchSynchronizedSimulation(){
        Nummer num = new NummerSync();
        for (int i = 0; i < 1000; i++){
            new Thread(new IncrementThread(num)).start();
        }
    }
}
