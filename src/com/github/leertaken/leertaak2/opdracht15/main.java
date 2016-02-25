package com.github.leertaken.leertaak2.opdracht15;

/**
 * Created by Hp user on 24-2-2016.
 */
public class main {
    public static void main(String[]args) {
        try{
            Loader loader = new Loader();
            new DecisionBuilder(loader.getInput());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
