package com.github.leertaken.leertaak2.opdracht15;

import javax.swing.*;
import javax.xml.soap.Node;
import java.io.File;
import java.util.Scanner;

/**
 * Created by Hp user on 24-2-2016.
 */
public class Loader{
    private Scanner input;

    public Loader(){
        try{
            loadingInput();
            buildTree();
        }
        catch (Exception e){}
    }

    private void loadingInput() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();

        if(fileChooser.showOpenDialog(null)==JFileChooser.OPEN_DIALOG){
            File file = fileChooser.getSelectedFile();
            input = new Scanner(file);
        }
    }

    private void buildTree(){
        Node iroot = null;


       // return null;

    }

    public Scanner getInput() {
        return input;
    }
}
