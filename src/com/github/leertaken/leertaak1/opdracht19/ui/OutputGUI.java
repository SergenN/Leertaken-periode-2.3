package com.github.leertaken.leertaak1.opdracht19.ui;


import javax.swing.*;
import java.awt.*;

/**
 * Created by Hp user on 17-2-2016.
 */
public class OutputGUI extends JPanel{
    private JLabel inputText = new JLabel();
    private JLabel steps = new JLabel();
    private JLabel history = new JLabel();
    private String insideHistory ="<html>";

    public OutputGUI(){
        setLayout(new GridLayout(2, 2));
        inputText.setText("0");
        steps.setText("");
        history.setText("");
        add(steps);
        add(history);
        add(inputText);
    }

    public void updateNumbers(String values,String steps){
        inputText.setText(values);
        this.steps.setText(steps);
    }

    public void addHistory(String history,String value){
        insideHistory += "<br>"+history+"="+value;
        this.history.setText(insideHistory+"</html>");
    }
    public String getInputText(){
        return inputText.getText();
    }
}