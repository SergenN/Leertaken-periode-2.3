package com.github.leertaken.leertaak2.Opdracht15V2.view;

import com.github.leertaken.leertaak2.Opdracht15V2.classifier.*;
import javafx.scene.layout.Border;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Michs on 8-3-2016.
 */
public class ViewTree extends JPanel {
    private DecisionTree decisionTree = null;
    private static final int X = 800, Y =600, X_OFFSET = 0, Y_OFFSET = 1;


    public ViewTree(DecisionTree decisionTree){
        this.decisionTree = decisionTree;
        //this.setPreferredSize(new Dimension(900,700));
        //this.setLayout(null);
        generateView();
    }

    public void generateView(){
        Node root = decisionTree.getRoot();
        JPanel jPanel = tekenNodes(root,X,Y,X_OFFSET,Y_OFFSET);
        this.add(jPanel);
    }


    private JPanel tekenNodes(Node node, int x, int y, int x_OFFSET, int y_OFFSET){
        JPanel jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createTitledBorder(node.getLabel()));
        //jPanel.setLayout(null);
        JLabel label = new JLabel(node.toString());
        if(node.isLeaf()){
            jPanel.setBackground(Color.red);
            label = new JLabel(node.getLabel());
        }

        label.setHorizontalAlignment(SwingConstants.CENTER);
        //label.setBounds(x-50, y,100,30);
        label.setBackground(Color.YELLOW);
        label.setOpaque(true);

        //System.out.println("New TekenNode");
        if(!node.isLeaf()){
            Map<String, Node> arcs = node.getArcs();
            int childerens = arcs.size();
            int minPosX = (x - x_OFFSET) / childerens;
            int rowsX = (x - x_OFFSET) / (childerens-1);
            int row = 0;

            for (Iterator<String> i= arcs.keySet().iterator(); i.hasNext();){
                String arcLabel = i.next();
                Node nextNode = arcs.get(arcLabel);
                //System.out.println("Iterating");
                int newX = minPosX +(rowsX * row);
                jPanel.add(tekenNodes(nextNode,newX,y, (rowsX*row*y_OFFSET),y_OFFSET+1));
                row++;
            }
        }
        jPanel.add(label);
        jPanel.setVisible(true);
        return jPanel;
    }
}
