package com.github.leertaken.leertaak2.opdracht15;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.DoubleUnaryOperator;

/**
 * Created by Hp user on 24-2-2016.
 */
public class DecisionBuilder {
    private int features,items;
    private Double startEntropy;
    private HashMap<String,Integer> availableChoices=new HashMap<>(), availabletempChoices= new HashMap<>();
    private Scanner file;

    public DecisionBuilder(Scanner file){
        this.file=file;
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
        startEntropy = calculateStartEntropy();
        for(int i = 0;i<2;i++) {
            Double leftEntropy = calculateLeftEntropy(i,2);
            System.out.println(leftEntropy);
        }

        //System.out.println("features: "+features+"\nItems:"+items+"\nRows:"+amountLooped);
    }

    private Double calculateStartEntropy(){
        System.out.println("Start Calculating");
        int[] tellerCalc1 = startEntropyTeller();
        Double result = new Double(0);
        for(int i = 0; i<tellerCalc1.length;i++){
            //System.out.println("Teller: "+tellerCalc1[i]);
            //System.out.println("Noemer: "+(availableChoices.size()+1));
            Double breuk = new Double(new Double(tellerCalc1[i])/new Double((availableChoices.size()+1)));
            //System.out.println("breuk: "+breuk);
            Double answer = new Double(-breuk*(Math.log(breuk)/Math.log(2)));
            //System.out.println("Log result: "+answer);
            if(i==0){
                result = answer;
            }else{
                result+=answer;
            }
        }
        return result;
    }

    private Double calculateLeftEntropy(int start, int end){
        System.out.println("Start Left  Calc");
        int[] tellerCalc = startEntropyTeller(start,end);
        Double result = new Double(0);
        for(int i = 0; i<tellerCalc.length;i++){
            Double breuk = new Double(new Double(tellerCalc[i])/new Double((availabletempChoices.size()+1)));
            Double answer = new Double(-breuk*(Math.log(breuk)/Math.log(2)));
            if(i==0){
                result=answer;
            }else{
                result+=answer;
            }
        }
        return result;
    }

    private int[] startEntropyTeller() {
        int teller[] = new int [availableChoices.size()];
        int i=0;
        for(String key: availableChoices.keySet()){
            teller[i]= availableChoices.get(key);
            i++;
        }
        return teller;
    }
    private int[] startEntropyTeller(int start, int end) {
        int teller[] = new int [end-start];
        availabletempChoices = new HashMap<>();
        getAvailableChoices(start,end);
        int i=0;
        for(String key: availabletempChoices.keySet()){
            teller[i] = availabletempChoices.get(key);
            i++;
        }
        return teller;
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
    private void getAvailableTempChoices(String[] keys){
        if(availableChoices.containsKey(keys[keys.length-1])){
            int ammount = availabletempChoices.get(keys[keys.length-1]);
            ammount = ammount+1;
            availabletempChoices.replace(keys[keys.length-1], ammount);
        }else{
            int ammount = 1;
            availabletempChoices.put(keys[keys.length-1],ammount);
        }
    }

    private void getAvailableChoices(int start,int end){
        start=start+2;
        end = end+2;
        int amountLooped=0;
        while (file.hasNext()){
            String line = file.nextLine();
            String keys[] = line.split(";");
            if(amountLooped<=1) {
            }else{
                if(amountLooped>=start&&amountLooped<=end) {
                    getAvailableTempChoices(keys);
                }
            }
            amountLooped++;
        }
    }
}
