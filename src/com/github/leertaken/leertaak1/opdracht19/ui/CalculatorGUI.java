package com.github.leertaken.leertaak1.opdracht19.ui;

import com.github.leertaken.leertaak1.opdracht19.ui.Command;
import com.github.leertaken.leertaak1.opdracht19.ui.NumberGUI;
import com.github.leertaken.leertaak1.opdracht19.ui.OutputGUI;

import java.awt.*;



import javax.swing.*;
import java.awt.*;
import java.awt.Color;

/**
 * Created by Hp user on 17-2-2016.
 */
public class CalculatorGUI extends JApplet{
    private Command command;
    private NumberGUI numberGUI;
    private OutputGUI outputGUI;

    public void init() {
        resize(400,300);
        command = new Command();

        outputGUI = new OutputGUI();
        outputGUI.setBackground(Color.WHITE);
        getContentPane().add(outputGUI);
        add(outputGUI);

        numberGUI = new NumberGUI();
        numberGUI.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(numberGUI);
        add(numberGUI);

        this.setVisible(true);
    }
}