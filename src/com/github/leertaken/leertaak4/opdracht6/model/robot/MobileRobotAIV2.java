package com.github.leertaken.leertaak4.opdracht6.model.robot;

import com.github.leertaken.leertaak4.opdracht6.model.virtualmap.OccupancyMap;

import java.io.*;
import java.util.DoubleSummaryStatistics;
import java.util.StringTokenizer;

/**
 * Created by pjvan on 18-4-2016.
 */
public class MobileRobotAIV2 implements Runnable{
    private static final int NORTH = 270, EAST = 360, SOUTH=90, WEST=180, FORWARD = 0, RIGHT = 90;

    private static final int MAX_VIEW = 10;

    private final OccupancyMap occupancyMap;
    private final MobileRobot mobileRobot;

    private PipedInputStream pipedInput;
    private BufferedReader input;
    private PrintWriter output;

    private String      result;
    private double[]    position = new double[3],  measures = new double[360];
    private double      startX,    startY;

    private boolean firstrun = true, running = true;

    public MobileRobotAIV2(MobileRobot mobileRobot, OccupancyMap occupancyMap){
        this.mobileRobot = mobileRobot;
        this.occupancyMap = occupancyMap;

        try{
            pipedInput = new PipedInputStream();
            input = new BufferedReader(new InputStreamReader(pipedInput));
            output = new PrintWriter(new PipedOutputStream(pipedInput),true);
            result = "";
        }
        catch (IOException e){
            e.printStackTrace();
        }
        mobileRobot.setOutput(output);
    }

    @Override
    public void run() {
        while (running){
            try{
                processScan();
                if(scannedMap()){
                    mobileRobot.quit();
                }
            }
            catch (IOException e){
                e.printStackTrace();
                running = false;
            }
        }
    }

    private void processScan() throws IOException{
        scanInit();

        int xCoord = (int) position[0] / occupancyMap.getCellDimension();
        int yCoord = (int) position[1] / occupancyMap.getCellDimension();

        int[] lookingDirection = lookingDirection(FORWARD);

        int xLooking = lookingDirection[0];
        int yLooking = lookingDirection[1];

        boolean end = false;

        char[][] map = occupancyMap.getGrid();

        int predictedSteps = 0;

        while (predictedSteps<MAX_VIEW && !end){
            boolean rightWall = searchRightWall(xCoord,yCoord);

            if(rightWall && map[xCoord][yCoord]==occupancyMap.getEmpty()){
                predictedSteps++;
            } else if (rightWall && map[xCoord][yCoord] == occupancyMap.getUnknown()){
                moveForward(predictedSteps-2);
                end = true;
            } else if (rightWall && map[xCoord][yCoord]==occupancyMap.getObstacle()){
                moveForward(predictedSteps-3);
                rotate("LEFT");
                end = true;
            } else if (!rightWall && map[xCoord][yCoord] == occupancyMap.getUnknown()){
                moveForward(predictedSteps-2);
                end = true;
            } else if (!rightWall && map[xCoord][yCoord] ==occupancyMap.getEmpty()){
                rightCorner(predictedSteps);
                end = true;
            }
            xCoord += xLooking;
            yCoord +=yLooking;
        }
        if(!end){
            moveForward(predictedSteps-2);
        }
    }

    private void rightCorner(int predictedSteps) throws IOException {
        moveForward(predictedSteps+3);
        scanInit();

        int xCoord = (int) position[0] / occupancyMap.getCellDimension();
        int yCoord = (int) position[1] / occupancyMap.getCellDimension();

        boolean rightWall = searchRightWall(xCoord,yCoord);

        if(!rightWall){
            rotate("RIGHT");
            moveForward(2);
        }
    }

    private boolean searchRightWall(int xCoord, int yCoord){
        boolean rightWall = true;

        int[] lookingDirection = lookingDirection(RIGHT);

        int xLooking = lookingDirection[0];
        int yLooking = lookingDirection[1];

        char[][] map = occupancyMap.getGrid();
        boolean end = false;

        int predictedStepsWall = 0;

        while (predictedStepsWall<6 &&!end){
            if(map[xCoord][yCoord]==occupancyMap.getUnknown()){
                end = true;
                rightWall =false;
            } else if( map[xCoord][yCoord] == occupancyMap.getObstacle()){
                end = true;
                rightWall = true;
            }
            predictedStepsWall++;
            xCoord += xLooking;
            yCoord +=yLooking;
        }
        if (!end){
            rightWall=false;
        }
        return rightWall;
    }

    private void moveForward(int steps)throws IOException{
        mobileRobot.sendCommand("P1.MOVEFW "+steps*occupancyMap.getCellDimension());
        result = input.readLine();
    }

    private void rotate(String direction) throws IOException{
        if(!direction.equals("RIGHT")&&!direction.equals("LEFT")){
            throw new IllegalArgumentException("Invalid Direction "+direction);
        }
        mobileRobot.sendCommand("P1.ROTATE"+direction+" 90");
        result = input.readLine();
    }

    private void scanInit() throws IOException{
        currentPos();
        scan("LASER");

        currentPos();
        scan("SONAR");

        if(position[0]!=startX && position[1]!=startY){
            firstrun=false;
        }

        if (!firstrun){
            startX = mobileRobot.getPlatform().getRobotPosition().getX();
            startY = mobileRobot.getPlatform().getRobotPosition().getY();
        }
    }

    private void scan(String device) throws IOException {

        switch (device.toUpperCase()){
            case "SONAR":
                device="S";
                break;
            case "LASER":
                device="L";
                break;
            default:
                throw new IllegalArgumentException("Invalid device "+device);
        }

        mobileRobot.sendCommand(device+"1.SCAN");
        try{
            result = input.readLine();
        }
        catch (InterruptedIOException e){result = input.readLine();}
        parseMeasure(result,measures);

        if(device.equals("S")){
            occupancyMap.drawSonarScan(position,measures);
        }else if (device.equals("L")){
            occupancyMap.drawLaserScan(position,measures);
        }
    }
    private void currentPos() throws IOException{
        mobileRobot.sendCommand("R1.GETPOS");
        result= input.readLine();
        parsePosition(result,position);
    }
    private int[] lookingDirection(int direction){
        int currentDirection = lookingClosest(position[2]);
        if(currentDirection==360 && direction>0){
            currentDirection=0;
        }
        currentDirection+=direction;
        int[] searchDirect = new int[2];

        switch (currentDirection){
            case NORTH:
                searchDirect[0] =0;
                searchDirect[1] = -1;
                break;
            case EAST:
                searchDirect[0]=1;
                searchDirect[1]=0;
                break;
            case SOUTH:
                searchDirect[0]=0;
                searchDirect[1]=1;
                break;
            case WEST:
                searchDirect[0]=-1;
                searchDirect[1]=0;
                break;
            default:
                throw new IllegalArgumentException(currentDirection+ " onbekende richting");
        }
        return searchDirect;
    }

    private int lookingClosest(double number){
        int closest;
        int north = (int) (NORTH-number);
        int east = (int) (EAST-number);
        int south = (int) (SOUTH-number);
        int west = (int) (WEST-number);

        if(north<2&&north>-2){
            closest = NORTH;
        }else if(east<2 &&east>-2 || east<362 && east>358){
            closest=EAST;
        }else if(south<2 && south>-2){
            closest= SOUTH;
        }else if (west<2 && west>-2){
            closest=WEST;
        }else{
            throw new IllegalArgumentException("Wrong direction");
        }
        return closest;
    }

    private boolean scannedMap(){
        boolean scannedMap = false;
        int xCoord = (int) position[0] / occupancyMap.getCellDimension();
        int yCoord = (int) position[1] / occupancyMap.getCellDimension();

        if(searchRightWall(xCoord,yCoord)){
            int[] search = lookingDirection(90);
            int xSearch = search[0];
            int ySearch = search[1];

            char[][]map = occupancyMap.getGrid();
            while (map[xCoord][yCoord]!=occupancyMap.getObstacle()){
                xCoord+=xSearch;
                yCoord+=ySearch;
            }

            boolean continued = true;
            int startX = xCoord;
            int startY = yCoord;

            int currentX = xCoord;
            int currentY = yCoord;

            int previouseX = startX;
            int previouseY = startY;

            do{
                try{
                    int[] wallCoords = searchWall(currentX,currentY,previouseX,previouseY);
                    previouseX = currentX;
                    previouseY = currentY;

                    currentX = wallCoords[0];
                    currentY = wallCoords[1];
                }
                catch (IllegalArgumentException a){
                    continued=false;
                }
            }
            while ((currentX!=startX||currentY!=startY)&&continued);
            if(continued){
                scannedMap = true;
            }
        }
        return scannedMap;
    }

    private int[] searchWall(int xCoord,int yCoord, int previouseX, int previouseY){
        int[] nextWall = new int[2];
        char[][]map = occupancyMap.getGrid();

        if(xCoord>0&&map[xCoord-1][yCoord]==occupancyMap.getObstacle()&&(xCoord - 1 != previouseX|| yCoord!=previouseY)){
            nextWall[0]=xCoord-1;
            nextWall[1] =yCoord;
        }else if(map[xCoord+1][yCoord]==occupancyMap.getObstacle() &&(xCoord+1!=previouseX||yCoord!=previouseY)){
            nextWall[0]=xCoord+1;
            nextWall[1]=yCoord;
        }else if (yCoord > 0 && map[xCoord][yCoord- 1] == occupancyMap.getObstacle() && (xCoord != previouseX|| yCoord - 1 != previouseY)) {
            nextWall[0] = xCoord;
            nextWall[1] = yCoord- 1;
        } else if (map[xCoord][yCoord+ 1] == occupancyMap.getObstacle() && (xCoord!= previouseX|| yCoord+ 1 != previouseY)) {
            nextWall[0] = xCoord;
            nextWall[1] = yCoord+ 1;
        } else {
            throw new IllegalArgumentException("");
        }
        return nextWall;
    }

    private void parsePosition(String value,double position[]){

        int start, end;
        String param;
        start = value.indexOf("X=");
        param = value.substring(start+2);
        end = param.indexOf(" ");
        position[0] = Double.parseDouble(param.substring(0,end));

        start = value.indexOf("Y=");
        param = value.substring(start+2);
        end = param.indexOf(" ");
        position[1] = Double.parseDouble(param.substring(0,end));

        start = value.indexOf("DIR=");
        param = value.substring(start+4);
        position[2] = Double.parseDouble(param);
    }

    private void parseMeasure(String value, double measures[]){
        for (int i = 0; i<360;i++){
            measures[i]=100.0;
        }

        if (value.length()>=5){
            value = value.substring(5);  // removes the "SCAN " keyword

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
                System.out.println("direction = " + direction + " distance = " + distance);
            }
        }
    }
}
