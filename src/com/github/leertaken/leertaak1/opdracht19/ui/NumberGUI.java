package com.github.leertaken.leertaak1.opdracht19.ui;

import com.github.leertaken.leertaak1.opdracht19.multiformat.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hp user on 17-2-2016.
 */
public class NumberGUI extends JPanel {
    private Calculator calculator;
    private OutputGUI outputGUI;
    private String values = "", operand= "", values2= "",steps="";
    private ArrayList<String> multistep = new ArrayList<>();
    private static final String DEC = "0123456789",HEX = "0123456789ABCDEF", OCT="01234567",BIN="01";
    private HashMap<String,JButton> buttonList = new HashMap<>();
    private boolean pressedEqual = false;

    public NumberGUI(Calculator calculator, OutputGUI outputGUI){
        this.calculator = calculator;
        this.outputGUI = outputGUI;

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new GridLayout(6,4,4,5));

        setupButtons();

    }
    private void setupButtons(){
        String[] knoppen = {
                "CE","Clear","dec","hex","oct","bin",
                "(",")","7", "8", "9", "/",
                "A","B","4", "5", "6", "*",
                "C","D","1", "2", "3", "-",
                "E", "F","0", ".",  "=", "+",
                "rational", "fixed", "float",  "",  "", "(+/-)",
        };

        for(int i=0; i<knoppen.length;i++) {
            if (knoppen[i] == "") {
                JLabel knop = new JLabel(knoppen[i]);
                add(knop);
            } else {
                JButton knop = new JButton(knoppen[i]);
                add(knop);
                try {
                    Double.parseDouble(knoppen[i]);
                    setValue(knop);
                    buttonList.put(knoppen[i],knop);
                } catch (NumberFormatException e) {
                    if("ABCDEF".contains(knoppen[i])){
                        setHexadecimal(knop);
                        buttonList.put(knoppen[i],knop);
                    }else if("+/*-".contains(knoppen[i])){
                        setOperand(knop);
                    }else if("hex, dec, oct, bin".contains(knoppen[i])){
                        setBase(knop);
                    }else if("Clear".contains(knoppen[i])){
                        setClear(knop);
                    }else if("CE".contains(knoppen[i])){
                        setClearAll(knop);
                    }else if("=".contains(knoppen[i])){
                        setEqual(knop);
                    }else if(".".contains(knoppen[i])){
                        setDot(knop);
                    }else if("()".contains(knoppen[i])){
                        setBraces(knop);
                    }else if("(+/-)".contains(knoppen[i])){
                        setNegative(knop);
                    }
                }
            }
        }
    }

    private void switchBase(){
        for(Map.Entry<String,JButton> button: buttonList.entrySet()){
            if(calculator.getBase().getDigits().contains(button.getKey())){
                button.getValue().setEnabled(true);
            }else {
                button.getValue().setEnabled(false);
            }
        }
        outputGUI.updateNumbers(calculator.secondOperand(),"");
        try{
            multistep.set(0,calculator.secondOperand());
        }
        catch (IndexOutOfBoundsException e){

        }
    }

    private void setBraces(JButton knop){

    }

    private void setNegative(JButton knop){
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(values.contains("-")){
                    values = values.substring(1);
                }else {
                    values = "-" + values;
                }
                outputGUI.updateNumbers(values,steps);
            }
        });
    }

    private void setHexadecimal(JButton knop){
        if(calculator.getBase()instanceof DecimalBase) {
            knop.setEnabled(false);
        }
        setValue(knop);
    }

    private void setValue(JButton knop){
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                values += e.getActionCommand().toString();
                //steps += e.getActionCommand().toString();
                outputGUI.updateNumbers(values,steps);
            }
        });
    }
    private void setDot(JButton knop){
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                values += e.getActionCommand().toString();
                //steps += e.getActionCommand().toString();
                outputGUI.updateNumbers(values,steps);
            }
        });
    }
    private void setOperand(JButton knop){
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pressedEqual=false;
                fullNumber();
                operand = knop.getText();
                steps += " "+e.getActionCommand().toString()+" ";
                outputGUI.updateNumbers(values,steps);
                checkValues();

            }
        });
    }

    private void setBase(JButton knop){
        String command = knop.getText();
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(command.equals("dec")) calculator.setBase(new DecimalBase());
                else if(command.equals("bin")) calculator.setBase(new BinaryBase());
                else if(command.equals("hex")) calculator.setBase(new HexBase());
                else if(command.equals("oct"))calculator.setBase(new OctalBase());
                System.out.println(command);
                switchBase();
            }
        });
    }

    private void setClear(JButton knop){
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                values="";
                //operand="";
                //steps="";
                outputGUI.updateNumbers(values,steps);
            }
        });
    }

    private void setClearAll(JButton knop){
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                values = "";
                operand = "";
                steps = "";
                values2 = "";
               // try {
                    //calculator.addOperand("0");
                    //calculator.addOperand("0");
                    calculator.delete();
                    multistep = new ArrayList<String>();
                    outputGUI.updateNumbers(values,steps);
               // }
                //catch (FormatException | NumberBaseException a){

                //}
            }
        });
    }

    private void checkValues(){
        System.out.println("Checking values");

        if(multistep.size()>2){
            System.out.println("calculating");
            try {
                System.out.println(multistep.toString());
                int max =0;
                while(multistep.size()>2&&max<100){
                    for(int i=2;i<multistep.size();i++) {
                        if ("+-/*".contains(multistep.get(i))) {
                            calculator.addOperand(multistep.get(i - 2));
                            calculator.addOperand(multistep.get(i - 1));
                            if (multistep.get(i).equals("+")) calculator.add();
                            else if (multistep.get(i).equals("-")) calculator.subtract();
                            else if (multistep.get(i).equals("*")) calculator.multiply();
                            else if (multistep.get(i).equals("/")) calculator.divide();
                            multistep.set(i, calculator.secondOperand());
                            multistep.remove(i - 2);
                            multistep.remove(i - 2);
                            break;
                        }
                        max++;
                    }
                }
                System.out.println(multistep.toString());
                outputGUI.updateNumbers(multistep.get(multistep.size() - 1), steps);

            }catch (NumberBaseException | NumberFormatException | FormatException e){
                e.getStackTrace();
            }
        }
    }

    private void setEqual(JButton knop){
        knop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullNumber();
                if(multistep.size()==1){
                    if(multistep.size()==1){
                        multistep.add("+");
                        multistep.add(0,"0");
                    }
                }

                outputGUI.updateNumbers(values,steps);


                checkValues();
                outputGUI.addHistory(steps,outputGUI.getInputText());
                steps = "";
            }
        });
    }

    /**
     * Change the previouse number to a full number
     */
    private void fullNumber(){
        if(pressedEqual){
           multistep= new ArrayList<>();
            steps ="";
        }
        if(values!="") {
            multistep.add(values);
            steps+=values;
        }
        values="";
        if(operand!=""){
            multistep.add(operand);
            operand="";
        }
    }
}
