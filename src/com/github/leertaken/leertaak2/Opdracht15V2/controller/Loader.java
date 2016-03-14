package com.github.leertaken.leertaak2.Opdracht15V2.controller;

import com.github.leertaken.leertaak2.Opdracht15V2.classifier.*;
import sun.security.krb5.internal.crypto.Des;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Hp user on 24-2-2016.
 */
public class Loader{
    private FileReader input;
    private static final String OPTIONS_FILE = "OptionText.txt";
    private ArrayList<Node> optionList = new ArrayList<>();

    public Loader(){
        try{
            loadingInput();
        }
        catch (Exception e){}
    }

    private void loadingInput() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();

        if(fileChooser.showOpenDialog(null)==JFileChooser.OPEN_DIALOG){
            input = new FileReader(fileChooser.getSelectedFile());
        }
        System.out.println("File has loaded");
    }

    public FileReader getInput() {
        return input;
    }


    private File makeAbsoluteFilename(String filename)
    {
        File file = new File(filename);
        if(!file.isAbsolute()) {
            file = new File(getProjectFolder(), filename);
        }
        return file;
    }
    private File getProjectFolder()
    {
        String myClassFile = getClass().getName() + ".class";
        URL url = getClass().getResource(myClassFile);
        return new File(url.getPath()).getParentFile();
    }
}
