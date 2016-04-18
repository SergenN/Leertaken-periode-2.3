package com.github.leertaken.leertaak4.opdracht6.model.robot;

import com.github.leertaken.leertaak2.opdracht10.Beslisboom;
import com.github.leertaken.leertaak4.opdracht6.model.virtualmap.OccupancyMap;
import javafx.util.converter.IntegerStringConverter;

import java.io.PipedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.PipedOutputStream;
import java.io.IOException;

import java.util.*;

/**
 * Title    :   The Mobile Robot Explorer Simulation Environment v2.0
 * Copyright:   GNU General Public License as published by the Free Software Foundation
 * Company  :   Hanze University of Applied Sciences
 *
 * @author Dustin Meijer        (2012)
 * @author Alexander Jeurissen  (2012)
 * @author Davide Brugali       (2002)
 * @version 2.0
 */

public class MobileRobotAI implements Runnable {

	private final OccupancyMap map;
	private final MobileRobot robot;

	private boolean running;

	public MobileRobotAI(MobileRobot robot, OccupancyMap map) {
		this.map = map;
		this.robot = robot;

	}

	/**
	 * In this method the gui.controller sends commands to the robot and its devices.
	 * At the moment all the commands are hardcoded.
	 * The exercise is to let the gui.controller make intelligent decisions based on
	 * what has been discovered so far. This information is contained in the OccupancyMap.
	 */
	public void run() {
		String result;
		this.running = true;
		double position[] = new double[3];
		double measures[] = new double[360];
		while (running) {
			try {

				PipedInputStream pipeIn = new PipedInputStream();
				BufferedReader input = new BufferedReader(new InputStreamReader(pipeIn));
				PrintWriter output = new PrintWriter(new PipedOutputStream(pipeIn), true);

				robot.setOutput(output);

//      ases where a variable value is never used after its assignment, i.e.:
				System.out.println("\nINFO: intelligence running");
				bestDirection(input,measures,position);
				//noAI(measures,position,input);
				System.out.println("INFO: bestIntell Ended");
				map.drawLaserScan(position, measures);
				//this.running = false;
			} catch (IOException ioe) {
				System.err.println("execution stopped");
				running = false;
			}
		}
	}

	private void parsePosition(String value, double position[]) {
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

	private void parseMeasures(String value, double measures[]) {
		for (int i = 0; i < 360; i++) {
			measures[i] = 100.0;
		}
		if (value.length() >= 5) {
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

	private void bestDirection(BufferedReader input, double[] measures, double[] position) throws IOException{
		System.out.println("\n (new loop)");
		String result;

		System.out.println("INFO: Initialize getting position");
		robot.sendCommand("R1.GETPOS");
		//try{wait(100);}catch (InterruptedException e){e.printStackTrace();}
		System.out.println("INFO: Robot sended command");
		result = input.readLine();
		System.out.println("INFO: Starting parse Data");
		parsePosition(result, position);
		System.out.println("INFO: Location completed");


		System.out.println("INFO: Initialize Scan");
		robot.sendCommand("L1.SCAN");
		//try{wait(100);}catch (InterruptedException e){e.printStackTrace();}
		System.out.println("INFO: Robot sended command");
		result = input.readLine();
		System.out.println("INFO: Scan completed");
		for(int i = 0; i<360;i++){
			measures[i]= 100.0;
		}
		if(result.length()>= 5 && result.contains("SCAN")){
			result = result.substring(5); // remove the SCAN Keyword
			System.out.println("TEST 1:"+result);
			StringTokenizer tokenizer = new StringTokenizer(result, " ");

			double distance;
			int direction =0;

			while (tokenizer.hasMoreTokens()){
				distance = Double.parseDouble(tokenizer.nextToken().substring(2));
				direction = (int) Math.round(Math.toDegrees(Double.parseDouble(tokenizer.nextToken().substring(2))));
				if(direction == 360){
					direction =0;
				}
				measures[direction] = distance;
			}
			HashMap<Integer,Integer> bestDirection = new HashMap<>();
			double longestRange=0.0;
			int beginStreak = 0;
			int endStreak = 0;
			int longestStreak=0;
			boolean streakKilled = false;
			for (int i = 0; i<360;i++){
				if(longestRange<measures[i]){
					longestRange=measures[i];
					streakKilled = false;
					if(beginStreak==0){
						beginStreak = i;
					}
					longestStreak++;
				}else {
					if(!streakKilled){

						direction =  Math.round((beginStreak+i)/2);
						System.out.println(beginStreak+" Begin Streak | End streak "+i+ " Direction "+direction+ " longest Streak "+longestStreak);
						bestDirection.put(longestStreak,direction);
						streakKilled = true;
						beginStreak=0;
						longestStreak=0;
					}
				}
			}
			Iterator iterator = bestDirection.keySet().iterator();
			System.out.println(bestDirection.size()+" Ways to go" );
			if(direction == 0) {
				System.out.println("INFO: moving "+longestRange);
				if(longestRange==100.0){
					longestRange=50;
				}
				robot.sendCommand("P1.MOVEFW "+longestRange);

			}else{
				Integer nextTurn = 0;
				while (iterator.hasNext()) {
					nextTurn = (Integer) bestDirection.get(iterator.next());
					System.out.println(nextTurn);
				}
				System.out.println(nextTurn);
				robot.sendCommand("P1.ROTATERIGHT " + nextTurn);
			}
		}else{
			System.err.println("WARNING: Result shorter than.\n"+result);
			return;
		}

	}

	private void noAI(double[] measures, double[] position, BufferedReader input) throws IOException{
		bestDirection(input,measures,position);

		System.out.println("continueing bad script");
		String result;
		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.MOVEBW 60");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.ROTATERIGHT 90");
		result = input.readLine();

		robot.sendCommand("P1.MOVEFW 100");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.ROTATELEFT 45");
		result = input.readLine();

		robot.sendCommand("P1.MOVEFW 70");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.MOVEFW 70");
		result = input.readLine();

		robot.sendCommand("P1.ROTATERIGHT 45");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.MOVEFW 90");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.ROTATERIGHT 45");
		result = input.readLine();

		robot.sendCommand("P1.MOVEFW 90");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.ROTATERIGHT 45");
		result = input.readLine();

		robot.sendCommand("P1.MOVEFW 100");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.ROTATERIGHT 90");
		result = input.readLine();

		robot.sendCommand("P1.MOVEFW 80");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
		map.drawLaserScan(position, measures);

		robot.sendCommand("P1.MOVEFW 100");
		result = input.readLine();

		robot.sendCommand("R1.GETPOS");
		result = input.readLine();
		parsePosition(result, position);

		robot.sendCommand("L1.SCAN");
		result = input.readLine();
		parseMeasures(result, measures);
	}
}
