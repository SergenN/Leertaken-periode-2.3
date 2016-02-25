package com.github.leertaken.leertaak2.opdracht10;

import java.util.ArrayList;

/**
 * Created by Hp user on 24-2-2016.
 */
public class main {
    public static void main(String[]args){
        Car mercedes = new Car("Mercedes SLK 2001   ",true,true);
        Car porsche  = new Car("Porsche 911 1982    ",true,false);
        Car renault  = new Car("Renault Laguna 1995 ",false,true);
        Car saab     = new Car("Saab Viggen 1975    ",false,false);

        ArrayList carList = new ArrayList<Car>();
        carList.add(mercedes);
        carList.add(porsche);
        carList.add(renault);
        carList.add(saab);


        Beslisboom.bereken(carList);


        Beslisboom.beslis(mercedes);
        Beslisboom.beslis(porsche);
        Beslisboom.beslis(renault);
        Beslisboom.beslis(saab);
    }


}
