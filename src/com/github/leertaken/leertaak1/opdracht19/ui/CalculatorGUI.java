package com.github.leertaken.leertaak1.opdracht19.ui;

import com.github.leertaken.leertaak1.opdracht19.multiformat.Calculator;
import com.github.leertaken.leertaak1.opdracht19.ui.Command;
import com.github.leertaken.leertaak1.opdracht19.ui.NumberGUI;
import com.github.leertaken.leertaak1.opdracht19.ui.OutputGUI;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Hp user on 17-2-2016.
 */
public class CalculatorGUI extends JApplet{
    private Command command;
    private NumberGUI numberGUI;
    private OutputGUI outputGUI;
    private Calculator calculator = new Calculator();

    public void init() {
        resize(600,400);
        setLayout(new GridLayout(2, 1));
        command = new Command();

        outputGUI = new OutputGUI();
        outputGUI.setBackground(Color.WHITE);
        getContentPane().add(outputGUI);


        numberGUI = new NumberGUI(calculator, outputGUI);
        numberGUI.setBackground(Color.LIGHT_GRAY);
        getContentPane().add(numberGUI);


        this.setVisible(true);


    }
}