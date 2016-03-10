package com.github.leertaken.leertaak2.Opdracht2;

import com.github.leertaken.leertaak2.Opdracht2.TreeClass;

/**
 * Created by Hp user on 22-2-2016.
 */
public class Main {
    public static void main(String[] args) {
        TreeClass treeClass= new TreeClass();
        System.out.println("#############################");
        treeClass.printBreedteOrdering();
        System.out.println("#############################");
        treeClass.printPreOrdering();
        System.out.println("#############################");
        treeClass.printPostOrdering();
    }
}

