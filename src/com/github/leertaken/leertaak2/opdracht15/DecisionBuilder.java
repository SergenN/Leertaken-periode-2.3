package com.github.leertaken.leertaak2.opdracht15;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Hp user on 24-2-2016.
 */
public class DecisionBuilder {
    private int features,items;
    private HashMap<String,Integer> availableChoices=new HashMap<>();

    public DecisionBuilder(Scanner file){
        int amountLooped= 0;
        while (file.hasNext()){
            String line = file.nextLine();
            String keys[] = line.split(";");
            if(amountLooped<=1) {
                if (amountLooped == 0) {
                    features = Integer.parseInt(keys[1]);
                }else if (amountLooped==1){
                    items = Integer.parseInt(keys[1]);
                }
            }else{
                getAvailableChoices(keys);

            }
            amountLooped++;
        }
        // End while
        calculateStartEntropy();


        //System.out.println("features: "+features+"\nItems:"+items+"\nRows:"+amountLooped);
    }

    private void calculateStartEntropy(){
        System.out.println("Start Calculating");
        int[] tellerCalc1 = startEntropy();
        Double result = new Double(0);
        for(int i = 0; i<tellerCalc1.length;i++){
            System.out.println("Teller: "+tellerCalc1[i]);
            System.out.println("Noemer: "+(availableChoices.size()+1));
            Double breuk = new Double(tellerCalc1[i]/(availableChoices.size()+1));
            System.out.println("breuk: "+breuk);
            Double answer = new Double(-breuk*Math.log(breuk));
            if(i==0){
                result = answer;
            }else{
                result = result+answer;
            }
        }
        System.out.println("Result: "+result);

    }


    private int[] startEntropy() {
        int returned[] = new int [availableChoices.size()];
        int i=0;
        for(String key: availableChoices.keySet()){
            returned[i]= availableChoices.get(key);
            i++;
        }
        return returned;
    }

    /**
     * Counting the catagories and putting them in a HashMap
     * @param keys
     */
    private void getAvailableChoices(String[] keys) {
        System.out.println("Checking"+keys[keys.length-1]);
        if(availableChoices.containsKey(keys[keys.length-1])){
            int ammount = availableChoices.get(keys[keys.length-1]);
            ammount = ammount+1;
            availableChoices.replace(keys[keys.length-1], ammount);
        }else{
            int ammount = 1;
            availableChoices.put(keys[keys.length-1],ammount);
        }
    }
}
