package com.github.leertaken.leertaak1.opdracht19.ui;

import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.*;
import java.awt.BorderLayout;

/**
 * Created by Hp user on 17-2-2016.
 */
public class NumberGUI extends JPanel {

    public NumberGUI(){

        this.setLayout(new BorderLayout(3,4));

        JButton zero = new JButton();
        zero.setText("0");
        add(zero);

        JButton one = new JButton();
        one.setText("1");
        add(one);

        JButton two = new JButton();
        two.setText("2");

        JButton three = new JButton();
        three.setText("3");

        JButton four = new JButton();
        four.setText("4");

        JButton five = new JButton();
        five.setText("5");

        JButton six = new JButton();
        six.setText("6");

        JButton seven = new JButton();
        seven.setText("7");

        JButton eight = new JButton();
        eight.setText("8");

        JButton nine = new JButton();
        nine.setText("9");


    }
}
