package com.github.leertaken.leertaak2.Opdracht15V2.view;

import com.github.leertaken.leertaak2.Opdracht15V2.classifier.*;
import com.github.leertaken.leertaak2.Opdracht15V2.controller.TreeBuilder;

import javax.swing.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;


/**
 * Created by Michs on 8-3-2016.
 */
public class GuiView extends JFrame {
    private DecisionTree tree;
    private ViewTree viewTree;
    private JScrollPane mainWindow;

    public GuiView() {
        mainWindow = new JScrollPane();
        //mainWindow.setPreferredSize(new Dimension(800,600));

        mainWindow.setVisible(true);

        this.setSize(900,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void loadFiles(FileReader fileReader){
        setLayout(new BorderLayout());
        try {
            /*FileReader fileReader = new FileReader(
                    this.getClass().getClassLoader().getResource("com/github/leertaken/leertaak2/Opdracht15V2/trainingSets/TrainingSet.txt").toString().substring(6)
            );*/
            FileReader fileReaderFeatures = new FileReader(
                    this.getClass().getClassLoader().getResource("com/github/leertaken/leertaak2/Opdracht15V2/controller/OptionText.txt").toString().substring(6)
            );
            TreeBuilder treeBuilder = new TreeBuilder(fileReader,fileReaderFeatures);
            tree = treeBuilder.buildTree();
            System.out.println("Creating new ViewTree");
            viewTree = new ViewTree(tree);
            System.out.println("Adding ViewTree on applet");
            mainWindow = new JScrollPane(viewTree);

            this.add(mainWindow,BorderLayout.CENTER);


            this.pack();
        }
        catch (FileNotFoundException  e){
            e.printStackTrace();
        }
    }

}