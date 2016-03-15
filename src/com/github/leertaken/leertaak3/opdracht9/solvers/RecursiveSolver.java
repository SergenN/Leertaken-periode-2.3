package com.github.leertaken.leertaak3.opdracht9.solvers;

import com.github.leertaken.leertaak3.opdracht9.Solver;

/**
 * Created by Michs on 15-3-2016.
 */
public class RecursiveSolver implements Solver {
    public RecursiveSolver(){}

    @Override
    public boolean solve(int[] numbers, int sum) {
        if(numbers.length==0){
            if(sum==0){
                return true;
            }else{
                return false;
            }
        }
        try {

            for (int i = 0; i < numbers.length; i++) {

                int somB = numbers[i];

                for (int j = i + 1; j < numbers.length; j++) {
                    somB += numbers[j];
                    if (somB == sum) {
                        return true;
                    }
                    if (somB - numbers[j - 1] == sum) {
                        return true;
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

}
