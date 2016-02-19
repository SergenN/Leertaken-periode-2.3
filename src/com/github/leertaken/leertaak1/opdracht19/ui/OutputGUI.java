package com.github.leertaken.leertaak1.opdracht19.ui;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Hp user on 17-2-2016.
 */
public class OutputGUI extends JPanel{
    private JLabel inputText = new JLabel();
    private JLabel steps = new JLabel();

    public OutputGUI(){
        setLayout(new GridLayout(2,0));
        add(steps);
        inputText.setText("0");
        steps.setText("");
        add(inputText);
    }

    public void updateNumbers(String values,String steps){
        inputText.setText(values);
        this.steps.setText(steps);
    }
}