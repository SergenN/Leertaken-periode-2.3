package com.github.leertaken.leertaak1.opdracht10;

/**
 * Created by Sergen Nurel
 * Date of creation 10-2-2016, 15:42
 * |
 * Authors: Sergen Nurel,
 * |
 * Version: 1.0
 * Package: com.github.leertaken.leertaak1
 * Class:
 * Description:
 * |
 * |
 * Changelog:
 * 1.0:
 */
public class ArrayMerger {

    protected static int[] array1 = {1,2,3};
    protected static int[] array2 = {3,4};

    public static void main(String[] args){
        ArrayMerger thisMerger = new ArrayMerger();
        try {
            thisMerger.mergeArray(array1, array2);
        }catch (ArraySizeException e){
            e.printStackTrace();
        }
    }


    protected int[] mergeArray(int[] array1, int[] array2) throws ArraySizeException{
        if (array1.length != array2.length){
            throw new ArraySizeException();
        }

        int[] toReturn = new int[array1.length];

        for (int i = 0; i < toReturn.length; i++){
            toReturn[i] = array1[i] + array2[i];
        }

        return toReturn;
    }
}
