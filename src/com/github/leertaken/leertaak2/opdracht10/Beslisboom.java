package com.github.leertaken.leertaak2.opdracht10;

import java.util.ArrayList;

/**
 * Created by Hp user on 24-2-2016.
 */
public class Beslisboom {


    public static void beslis(Car car){
        int value = 0;
        String category ="";
        String functions="";
        if(car.hasABS()){
            value +=1;
            functions=   " met ABS ";
        }
        if(car.hasAC()){
            value+=2;
            functions += " met AC  ";
        }

        if(value>=2){
            category = "Hoge  ";
        }else if(value ==1){
            category = "Midden";
        }else{
            category = "Lage  ";
        }
        System.out.println(car.getName()+" is een "+category+" klasse voertuig "+functions);
    }

    public static void bereken(ArrayList carList){
        int features = 0;
        for(int i=0; i>carList.size();i++){
            Car auto = (Car) carList.get(i);
            if(auto.getFeatures()>features){
                features=auto.getFeatures();
            }
        }
        if(features==0){
            System.out.println("Geen enkele auto heeft een feature, dit maakt het ");
        }
    }


}
