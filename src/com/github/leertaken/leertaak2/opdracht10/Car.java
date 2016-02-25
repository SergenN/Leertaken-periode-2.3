package com.github.leertaken.leertaak2.opdracht10;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * Created by Hp user on 24-2-2016.
 */
public class Car {
    private Boolean ac, abs;
    private String name;

    /**
     *
     * @param name
     * @param ac
     * @param abs
     */
    public Car(String name,Boolean ac, Boolean abs){
        this.name=name;
        this.ac=ac;
        this.abs=abs;
    }

    public Boolean hasABS() {return abs;}
    public Boolean hasAC()  {return ac;}
    public String getName() {return name;}
    public int getFeatures(){
        int counted = 0;
        if(ac){counted++;}
        if(abs){counted++;}
        return counted;
    }
}
