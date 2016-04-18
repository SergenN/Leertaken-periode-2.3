package com.github.leertaken.leertaak1.opdracht8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Created by Hp user on 10-2-2016.
 */
public class Statistieken extends JPanel implements ActionListener {
    private JLabel infoPanel = new JLabel();
    private JLabel aantalWorpenVeld = new JLabel();
    LinkedList<JLabel> aantalOpOgen = new LinkedList<>();
    private int worpen;
    private int[] aantallen;
    DobbelsteenModel d;


    public Statistieken() {
        worpen = 0;
        aantallen = new int[6];
        infoPanel.setText("aantal worp(en): ");
        //aantalWorpenVeld

        this.add(infoPanel);
        this.add(aantalWorpenVeld);
        for(int i = 0; i<6; i++){
            aantallen[i] = 0;
            JLabel jLabel = new JLabel();
            jLabel.setText("0");
            aantalOpOgen.push(jLabel);
            JLabel infoOog = new JLabel();
            infoOog.setText("aantal " + -(i-6) + "-en:");
            this.add(infoOog);
            this.add(jLabel);
        }
        this.setLayout(new GridLayout(8,1));


    }

    public void actionPerformed(ActionEvent e) {
        d = (DobbelsteenModel) e.getSource();
        worpen = worpen+1;
        aantalWorpenVeld.setText(""+worpen);
        int waarde = Integer.parseInt(aantalOpOgen.get(d.getWaarde() - 1).getText());
        waarde = waarde+1;
        aantalOpOgen.get(d.getWaarde()-1).setText(""+waarde);
        //steenRoodVeld.setText("" + d.getWaarde());
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }
}

