package com.github.leertaken.leertaak5.MobileRobot.gui;

import com.github.leertaken.leertaak5.MobileRobot.gui.controllers.DelayController;
import com.github.leertaken.leertaak5.MobileRobot.gui.controllers.MenuBarController;
import com.github.leertaken.leertaak5.MobileRobot.gui.controllers.OccupancyMapController;
import com.github.leertaken.leertaak5.MobileRobot.gui.controllers.SimulationController;
import com.github.leertaken.leertaak5.MobileRobot.gui.views.OccupancyMapView;
import com.github.leertaken.leertaak5.MobileRobot.gui.views.SimulationView;
import com.github.leertaken.leertaak5.MobileRobot.models.environment.Environment;
import com.github.leertaken.leertaak5.MobileRobot.models.virtualmap.OccupancyMap;
import com.github.leertaken.leertaak5.MobileRobot.utils.ANSI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        System.out.println(ANSI.ANSI_BLUE + "Mobile Robot");
        MainFrame runner = new MainFrame();
        runner.init();
    }

    public void init() {
        if (this.isVisible()) {
            this.setContentPane(new JPanel());
        }

        OccupancyMap map = new OccupancyMap();
        Environment environment = new Environment(map);
        map.setEnvironment(environment);

        SimulationController occupancyMenu = new SimulationController(environment.getRobot(), this);
        OccupancyMapController simulationMenu = new OccupancyMapController(environment);
        DelayController settingsMenu = new DelayController(environment);

        JMenuBar menuBar = new MenuBarController(
                new JMenu[]{
                        occupancyMenu,
                        simulationMenu,
                        settingsMenu
                }
        );

        SimulationView simulationView = new SimulationView(environment);
        simulationView.validate();

        OccupancyMapView mapView = new OccupancyMapView(map);
        mapView.validate();

        map.addActionListener(mapView);
        environment.addActionListener(simulationView);

        JTabbedPane left = new JTabbedPane();
        left.add(mapView, "Map view");

        JTabbedPane right = new JTabbedPane();
        right.add(simulationView, "Simulation view");

        this.setLayout(new GridLayout(1, 0));
        this.setJMenuBar(menuBar);
        this.add(left);
        this.add(right);

        left.validate();
        right.validate();
        left.setVisible(true);
        right.setVisible(true);

        this.setSize(1024, 560);
        this.validate();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Mobile Robot Explorer ~ by Nils Berlijn & Tom Broenink (2015), based on Davide Brugali (2002)");
        this.setVisible(true);
    }

}
