package com.github.leertaken.leertaak4.opdracht6.model.robot;

import com.github.leertaken.leertaak4.opdracht6.model.virtualmap.OccupancyMap;

import java.io.PipedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.PipedOutputStream;
import java.io.IOException;

import java.util.StringTokenizer;


/**
 * Title : The Mobile Robot Explorer Simulation Environment v2.0 Copyright: GNU
 * General Public License as published by the Free Software Foundation Company :
 * Hanze University of Applied Sciences
 *
 * @author Dustin Meijer (2012)
 * @author Alexander Jeurissen (2012)
 * @author Davide Brugali (2002)
 * @version 2.0
 */

public class MobileRobotAI implements Runnable {
    private final OccupancyMap map;
    private final MobileRobot robot;

    private BufferedReader input;

    private boolean running;


    private double[] position;
    private double[] measures;
    private String result;

    private static final int NORTH = 360;
    private static final int EAST = 90;
    private static final int SOUTH = 180;
    private static final int WEST = 270;

    private static final int WALL_MARGIN = 2;
    private static final int LASER_RANGE = 10;

    public MobileRobotAI(MobileRobot robot, OccupancyMap map) {
        this.map = map;
        this.robot = robot;

    }

    /**
     * In this method the gui.controller sends commands to the robot and its
     * devices. At the moment all the commands are hardcoded. The exercise is to
     * let the gui.controller make intelligent decisions based on what has been
     * discovered so far. This information is contained in the OccupancyMap.
     */
    public void run() {
        this.running = true;
        this.position = new double[3];
        this.measures = new double[360];
        this.result = "";

        boolean start = true;
        boolean turnRight = false;

        System.out.println("Robot is starting");

        while (running) {
            try {

                PipedInputStream pipeIn = new PipedInputStream();
                input = new BufferedReader(new InputStreamReader(pipeIn));
                PrintWriter output = new PrintWriter(new PipedOutputStream(pipeIn), true);

                robot.setOutput(output);

                while (start) {
                    scan();
                    //TODO: Scan sonar
                    if (!wallForward()) {
                        moveForward(1);
                    } else {
                        System.out.println("found a starting wall, turn left");
                        turnLeft();
                        start = false;
                    }
                }

                scan();

                if (wallRight() && wallForward()) {
                    System.out.println("Turning left");
                    turnLeft();
                    continue;
                } else if (turnRight) {
                    System.out.println("Turning right");
                    turnRightAroundWall();
                    turnRight = false;
                    continue;
                } else if (wallRight() && !turnRight) {
                    int stepsforward = moveBlocksForward(true);
                    System.out.println("Steps forward: "+stepsforward);
                    if (stepsforward == 0) {
                        turnRight = true;
                    } else {
                        moveForward(stepsforward);
                        System.out.println("Going forward");
                    }
                } else {
                    System.out.println("x=" + position[0] + " y=" + position[1] + " d=" + position[2]);
                    System.err.println("Something went wrong");
                }

                if(mapComplete()){
                    System.out.println("We're done, shutting down now");
                    running=false;
                }
            } catch (IOException ioe) {
                System.err.println("execution stopped");
                running = false;
            }
        }
    }

    private int moveBlocksForward(boolean wallmode){
        int positions[] = convertPosition();
        int x = positions[0];
        int y = positions[1];
        int direction = positions[2];

        int directionToRight = getDirectiontoRight(direction);

        int[] rightWall = getWallFromMap(x, y, directionToRight);

        int wallX = rightWall[0];
        int wallY = rightWall[1];

        int blocksToMove = 0;

        char[][] mapCopy = map.getGrid();

        for (int i = 1; i < LASER_RANGE; i++) {
            blocksToMove = i;
            char forwardBlock = getCharForward(x, y, direction, i, mapCopy);
            System.out.println("Forward scan - Found char: " + forwardBlock);

            if (forwardBlock == map.getObstacle() ||
                    forwardBlock == map.getUnknown() ||
                    (forwardBlock != map.getEmpty() && forwardBlock !=  'r')
                    ) {
                System.out.println("Forward scan - Found wall/unknown: " + i);
                blocksToMove--;
                break;
            }

        }
        blocksToMove = blocksToMove - WALL_MARGIN;
        System.out.println("Forward scan - Max distance: " + blocksToMove);

        if (wallmode) {
            for (int i = 1; i < LASER_RANGE; i++) {
                char wallBlock = getCharForward(wallX, wallY, direction, i, mapCopy);
                System.out.println("Wall scan - Found char: " + wallBlock);

                if (wallBlock == map.getEmpty()) {
                    System.out.println("Forward Wall - Found end of wall: " + i);
                    i--;
                    System.out.println("Forward Wall, Blocks: " + blocksToMove + "i: " + i);
                    if (i < blocksToMove) {
                        blocksToMove = i;
                    }
                    break;
                }
            }
        }
        System.out.println("Forward wall: " + blocksToMove);
        return blocksToMove;
    }

    private boolean wallRight(){
        char[][] mapCopy = map.getGrid();
        int positions[] = convertPosition();

        int direction = getDirectiontoRight(positions[2]);

        int[] wallCoordinates = getWallFromMap(positions[0], positions[1], direction);


        if (mapCopy[wallCoordinates[0]][wallCoordinates[1]] == map.getObstacle()) {
            return true;
        }
        return false;
    }

    private boolean wallForward(){
        char[][] mapCopy = map.getGrid();
        int positions[] = convertPosition();
        int offsetX = 1;
        int offsetY = 0;
        if(positions[2]==EAST||positions[2]==WEST){
            offsetY = 1;
            offsetX = 0;
        }
        for(int i=-2 ; i<=2;i++){
            int[] wallCoordinates = getWallFromMap(positions[0]+i*offsetY,positions[1]+i*offsetX,positions[2]);
            if (mapCopy[wallCoordinates[0]][wallCoordinates[1]] == map.getObstacle()) {
                return true;
            }
        }
        return false;
    }

    private int[] getWallFromMap(int x, int y, int direction) {
        int[] wallCoordinates = new int[2];

        if(direction==0){
            direction=360;
        }
        switch (direction) {
            case WEST:
                y -= (1 + WALL_MARGIN);
                break;
            case NORTH:
                x += (1 + WALL_MARGIN);
                break;
            case EAST:
                y += (1 + WALL_MARGIN);
                break;
            case SOUTH:
                x -= (1 + WALL_MARGIN);
                break;
        }
        wallCoordinates[0] = x;
        wallCoordinates[1] = y;
        return wallCoordinates;
    }

    private char getCharForward(int x, int y, int direction, int forward, char[][] mapCopy) {
        //TODO: add range
        char distance = 0;
        if(x+forward>mapCopy.length||y+forward>mapCopy[0].length||x-forward<0||y-forward<0){
            return distance;
        }
        try {
            if(direction==0){
                direction=360;
            }
            switch (direction) {
                case WEST:
                    distance = mapCopy[x][y - forward];
                    break;
                case NORTH:
                    distance = mapCopy[x + forward][y];
                    break;
                case EAST:
                    distance = mapCopy[x][y + forward];
                    break;
                case SOUTH:
                    distance = mapCopy[x - forward][y];
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array out of bounds error, still continuing.");
        }
        return distance;
    }

    private double[] laserScan() throws IOException {
        robot.sendCommand("R1.GETPOS");
        this.result = input.readLine();
        parsePosition(result);

        robot.sendCommand("L1.SCAN");
        String result = input.readLine();
        double[] measures = parseMeasures(result);
        map.drawLaserScan(position, measures);
        return measures;
    }

    private double[] sonarScan() throws IOException {
        robot.sendCommand("R1.GETPOS");
        this.result = input.readLine();
        parsePosition(result);

        robot.sendCommand("S1.SCAN");
        String result = input.readLine();
        double[] measures = parseMeasures(result);
        map.drawSonarScan(position, measures);
        return measures;
    }

    private void scan() throws IOException {
        double[] laserMeasures = laserScan();
        double[] sonarMeasures = sonarScan();
        for (int i = 0; i < 360; i++) {
            if (laserMeasures[i] < 0) {
                measures[i] = sonarMeasures[i];
            } else if (sonarMeasures[i] < 0) {
                measures[i] = laserMeasures[i];
            } else {
                measures[i] = Math.min(laserMeasures[i], sonarMeasures[i]);
            }
            // System.out.println("Laser: " + laserMeasures[i] + "; Sonar: " + sonarMeasures[i] + "; Measure: " + measures[i]);
        }
    }

    private void moveForward(int distance) throws IOException {
        robot.sendCommand("P1.MOVEFW " + Integer.toString(distance*10));
        result = input.readLine();
    }

    private void turnRight() throws IOException {
        robot.sendCommand("P1.ROTATERIGHT 90");
        result = input.readLine();
    }

    private void turnLeft() throws IOException {
        robot.sendCommand("P1.ROTATELEFT 90");
        result = input.readLine();
    }

    private void turnRightAroundWall(){
        try {
            moveForward(WALL_MARGIN + 1);

            turnRight();

            // Go WALL_DISTANCE + 1 forward
            moveForward(WALL_MARGIN + 1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean mapComplete(){
        char[][] mapCopy = map.getGrid();
        for (int i = 0; i < mapCopy.length; i++) {
            for (int j = 0; j < mapCopy[0].length; j++) {
                if(mapCopy[i][j] == map.getEmpty()){
                    if(borderSquare(i,j,map.getUnknown())){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean borderSquare(int row, int column, char cardChar){
        //row-1
        if(row>0){
            if(map.getGrid()[row-1][column] == cardChar){
                return true;
            }
        }
        //row+1
        if(row<map.getGrid().length-1){
            if(map.getGrid()[row+1][column] == cardChar){
                return true;
            }
        }
        //col-1
        if(column>0){
            if(map.getGrid()[row][column-1] == cardChar){
                return true;
            }
        }
        //col+1
        if(column<map.getGrid()[0].length-1){
            if(map.getGrid()[row][column+1] == cardChar){
                return true;
            }
        }
        return false;
    }

    private int getDirectiontoRight(int direction) {
        if (direction == 360) {
            direction = 90;
        } else {
            direction += 90;
        }

        return direction;
    }

    private int[] convertPosition() {
        int[] positions = new int[3];

        int x = ((int) Math.round(position[0]) / 10);
        int y = ((int) Math.round(position[1]) / 10);
        int direction = (int) Math.round(position[2]);
        if(direction==0){
            direction=360;
        }

        positions[0] = x;
        positions[1] = y;
        positions[2] = direction;

        return positions;
    }

    private void parsePosition(String value) {
        int indexInit;
        int indexEnd;
        String parameter;

        indexInit = value.indexOf("X=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[0] = Double.parseDouble(parameter.substring(0, indexEnd));

        indexInit = value.indexOf("Y=");
        parameter = value.substring(indexInit + 2);
        indexEnd = parameter.indexOf(' ');
        position[1] = Double.parseDouble(parameter.substring(0, indexEnd));

        indexInit = value.indexOf("DIR=");
        parameter = value.substring(indexInit + 4);
        position[2] = Double.parseDouble(parameter);

    }

    private double[] parseMeasures(String value) {
        double[] measures = new double[360];
        for (int i = 0; i < 360; i++) {
            measures[i] = 100.0;
        }
        if (value.length() >= 5) {
            value = value.substring(5); // removes the "SCAN " keyword

            StringTokenizer tokenizer = new StringTokenizer(value, " ");

            double distance;
            int direction;
            while (tokenizer.hasMoreTokens()) {
                distance = Double.parseDouble(tokenizer.nextToken().substring(2));
                direction = (int) Math.round(Math.toDegrees(Double.parseDouble(tokenizer.nextToken().substring(2))));
                if (direction == 360) {
                    direction = 0;
                }
                measures[direction] = distance;
                // Printing out all the degrees and what it encountered.
                // System.out.println("direction = " + direction + " distance = " + distance);
            }
        }
        return measures;
    }
}