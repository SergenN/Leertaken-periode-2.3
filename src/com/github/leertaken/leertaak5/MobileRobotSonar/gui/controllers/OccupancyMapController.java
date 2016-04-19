package com.github.leertaken.leertaak5.MobileRobotSonar.gui.controllers;

import com.github.leertaken.leertaak5.MobileRobotSonar.models.environment.Environment;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 *
 * @author Alexander Jeurissen  (2012)
 * @author Dustin Meijer        (2012)
 *
 * @version 2.0
 */


public class OccupancyMapController extends JMenu implements ActionListener {

    private final Environment environment;

    private JMenuItem menuFileOpenMap;
    private JMenuItem menuFileExit;
    private JMenu defaultmaps;
    private JMenuItem subMap, subSonar, subNoSonar;


    public OccupancyMapController(Environment environment) {
        super("File");

        this.environment = environment;

        this.menuFileOpenMap = new JMenuItem();
        this.menuFileExit = new JMenuItem();
        // --------------------------- Menu ----------------------------------------

        // Menu File Open Map
        this.menuFileOpenMap = new JMenuItem("Open Map");
        this.menuFileOpenMap.addActionListener(this);

        //Mich Open default Maps
        subMap 	= new JMenuItem("Map");
        subSonar 	= new JMenuItem("Map met Sonar");
        subNoSonar = new JMenuItem("Map zonder Sonar");
        subMap.addActionListener(this);
        subSonar.addActionListener(this);
        subNoSonar.addActionListener(this);
        defaultmaps = new JMenu("Standaard mappen");
        defaultmaps.add(subMap);
        defaultmaps.add(subSonar);
        defaultmaps.add(subNoSonar);

        // Menu File Exit
        this.menuFileExit = new JMenuItem("Exit");
        this.menuFileExit.addActionListener(this);

        // Adds the menu components
        this.add(menuFileOpenMap);
        this.add(defaultmaps);
        this.add(menuFileExit);


    }


    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(this.menuFileOpenMap)) {
            try{
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception a){}
            JFileChooser chooser = new JFileChooser(new File("c:"));

            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (environment.loadMap(chooser.getSelectedFile())){

                } else {
                    JOptionPane.showMessageDialog(null, "This is not a valid Map file!");
                }
            }
        } else if (e.getSource().equals(this.menuFileExit)) {
            System.exit(0);
        } else if(e.getSource().equals(this.subMap)){
            File file = new File(this.getClass().getClassLoader().getResource("com/github/leertaken/leertaak5/MobileRobotSonar/defaultMaps/map.xml").toString().substring(6));
            environment.loadMap(file);
        } else if(e.getSource().equals(this.subSonar)){
            File file = new File(this.getClass().getClassLoader().getResource("com/github/leertaken/leertaak5/MobileRobotSonar/defaultMaps/MapMetSonarTest.xml").toString().substring(6));
            environment.loadMap(file);
        } else if(e.getSource().equals(this.subNoSonar)){
            File file = new File(this.getClass().getClassLoader().getResource("com/github/leertaken/leertaak5/MobileRobotSonar/defaultMaps/ZonderSonarTest.xml").toString().substring(6));
            environment.loadMap(file);
        }
    }
}