package com.github.leertaken.leertaak2.opdracht7;

/**
 * Created by Sergen Nurel
 * Date of creation 23-2-2016, 14:05
 * |
 * Authors: Sergen Nurel,
 * |
 * Version: 1.0
 * Package: com.github.leertaken.leertaak2.opdracht7
 * Class:
 * Description:
 * |
 * |
 * Changelog:
 * 1.0:
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class GetalRij {
    private int[] getallen;

    public GetalRij(int aantal, int max){
        getallen = new int[aantal];
        vulArrayMetUniekeWaarden( aantal, max );
    }

    private void vulArrayMetUniekeWaarden(int aantal, int max) {
        // Vul een hulplijst met getallen 0, ..., max
        ArrayList hulpLijst = new ArrayList( max );
        for ( int i=0; i<max; i++){
            hulpLijst.add( i );
        }

        // Stop 'aantal' random waarden in getallen
        Random r = new Random();
        for ( int i=0; i<aantal; i++){
            // Het omzetten van Integer naar int gaat sinds Java 1.5 automatisch (unboxing).
            int getal = (Integer) (hulpLijst.remove( r.nextInt( hulpLijst.size())));
            getallen[i] = getal;
        }
    }

    public boolean zitErinA(int zoekWaarde){
        return false;
    }

    public boolean zitErinB(int zoekWaarde){
        return false;
    }

    public boolean zitErinC(int zoekWaarde){
        for (int i : getallen){
            if (i == zoekWaarde){
                return true;
            }
        }
        return false;
    }

    public boolean zitErinD(int zoekWaarde){
        return Arrays.binarySearch(getallen, zoekWaarde) != -1;
    }

    public void sorteer(){
        Arrays.sort( getallen);
    }

    public int getLastNum(){
        return getallen[getallen.length-1];
    }

    public void print(){
        for( int i=0; i<getallen.length; i++)
            System.out.println(getallen[i]);
    }

}
