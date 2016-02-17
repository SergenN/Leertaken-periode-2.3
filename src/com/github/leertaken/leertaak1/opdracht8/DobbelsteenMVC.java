package com.github.leertaken.leertaak1.opdracht8;

import javax.swing.*;
import java.awt.*;

public class DobbelsteenMVC extends JApplet {
    DobbelsteenModel model;             //het model
    TekstView tekstView;              // view
    DobbelsteenView dobbelsteenView;  // view
    DobbelsteenController controller;            // Controller
    Statistieken statistieken ;

    /*public static void main(String[] args){
        init();
    }*/

    public void init() {
        resize(500, 400);

        // Maak het model
        model = new DobbelsteenModel();

        // Maak de controller en geef hem het model
        controller = new DobbelsteenController(model);
        controller.setBackground(Color.cyan);
        getContentPane().add(controller, BorderLayout.NORTH);

        // Maak de views
        dobbelsteenView = new DobbelsteenView(Color.red);
        dobbelsteenView.setBackground(Color.black);
        getContentPane().add(dobbelsteenView, BorderLayout.CENTER);
        tekstView = new TekstView();
        tekstView.setBackground(Color.green);
        getContentPane().add(tekstView, BorderLayout.SOUTH);
        statistieken = new Statistieken();
        statistieken.setBackground(Color.orange);
        getContentPane().add(statistieken, BorderLayout.EAST);


        // Registreer de views bij het model
        model.addActionListener(tekstView);
        model.addActionListener(dobbelsteenView);
        model.addActionListener(statistieken);

        // Eerste worp
        model.gooi();
    }
}
