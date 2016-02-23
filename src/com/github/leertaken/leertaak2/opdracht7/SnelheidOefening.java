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

public class SnelheidOefening {

    public static void main( String[] args){
        long tijd;
        boolean gevonden;

        GetalRij gr = new GetalRij(100000, 100000);
        gr.sorteer();
        System.out.println(gr.getLastNum());
        tijd = tijd();
        System.out.print("Liniair zoeken: ");
        gr.zitErinC(gr.getLastNum());
        System.out.println(tijd() - tijd);

        tijd = tijd();
        System.out.print("Binair zoeken: ");
        gr.zitErinD(gr.getLastNum());
        System.out.println(tijd() - tijd);
    }

    private static long tijd(){
        return System.nanoTime();
    }

}
