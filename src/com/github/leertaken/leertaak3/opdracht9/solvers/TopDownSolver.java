package com.github.leertaken.leertaak3.opdracht9.solvers;

import com.github.leertaken.leertaak3.opdracht9.Solver;

/**
 * Created by Michs on 15-3-2016.
 */
public class TopDownSolver implements Solver {
    private int[][] tabel;

    @Override
    public boolean solve(int[] numbers, int sum) {
        if(numbers.length==0){
            if(sum==0){
                return true;
            }else{
                return false;
            }
        }
        int som = 0;
        for (int i=0;i<numbers.length;i++){
            if(numbers[i]>=0){
                som +=numbers[i];
            }
        }
        tabel = new int[numbers.length][som];

        for (int i = 0; i<numbers.length;i++){
            int somB = 0;
            for (int j = 0; j<=i;j++){
                tabel[i][numbers[j]-1]=1;
                somB += numbers[j];
                tabel[i][somB -1] = 1;

                if(j>=1){
                    som = somB - numbers[j-1];
                    tabel[i][som -1] =1;
                }
            }
        }



        return oplossing(sum, numbers);
    }

    private boolean oplossing(int sum, int[] numbers){
        if(sum<= tabel[0].length) {
            outputPrint(sum,numbers);
            for (int i = tabel.length - 1; i >= 0; i--) {
                if (tabel[i][sum - 1] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private void outputPrint(int sum, int[] numbers){
        System.out.println("TopDown");
        int rows = tabel.length;
        int cols = tabel[0].length;
        System.out.print("     ");
        for (int col=1;col<=cols;col++){
            System.out.format("%-3d",col);
        }
        System.out.println("");
        for (int row = rows-1; row>=0;row--){
            String output = String.format("%3d",numbers[row])+ ": ";
            for (int col = 0; col<cols;col++){
                output += (tabel[row][col]==1) ? 1+"  " : 0+"  ";
            }
            System.out.println(output);
        }
        System.out.println();
    }
}
