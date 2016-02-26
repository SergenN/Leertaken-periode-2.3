package com.github.leertaken.leertaak2.Opdracht15V2.controller;

import com.github.leertaken.leertaak2.Opdracht15V2.classifier.*;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Hp user on 24-2-2016.
 */
public class Loader{
    private File input;
    private static final String OPTIONS_FILE = "OptionText.txt";
    private ArrayList<Node> optionList = new ArrayList<>();

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
            input = fileChooser.getSelectedFile();
        }
    }

    private DecisionTree buildTree(){
        Node iroot = null;
        File optionFile = makeAbsoluteFilename(OPTIONS_FILE);
        BufferedReader reader = null;

        BufferedReader carReader = null;

        try {
            reader = new BufferedReader( new FileReader(optionFile));
            carReader = new BufferedReader(new FileReader(input));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        try{
            String line = reader.readLine();
            int lineNumber= 1;
            Node root = new Node(line);
            optionList.add(root);
            line = reader.readLine();
            while ((line !=null)){
                for(int i = 0; i<(Math.pow(2,lineNumber));i++){
                    Node node =  new Node(line);
                    optionList.add(node);
                }
                line = reader.readLine();
                lineNumber ++;
            }

            int group = 0;
            //int[]  carGroup = new int[]{}
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }

    public File getInput() {
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
