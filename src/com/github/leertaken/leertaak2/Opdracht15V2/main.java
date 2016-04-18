package com.github.leertaken.leertaak2.Opdracht15V2;

import com.github.leertaken.leertaak2.Opdracht15V2.controller.Loader;
import com.github.leertaken.leertaak2.Opdracht15V2.view.GuiView;

/**
 * Created by Hp user on 26-2-2016.
 */
public class main {
    public static void main(String[] args){
        Loader loader = new Loader();
        System.out.println("GUI View initalizing");
        GuiView guiView = new GuiView();
        System.out.println("GUI View Has been Initialized \nLoading Tree");
        guiView.loadFiles(loader.getInput());
    }
}
