package com.github.leertaken.leertaak4.opdracht6.model.device;

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


import com.github.leertaken.leertaak4.opdracht6.model.environment.Environment;
import com.github.leertaken.leertaak4.opdracht6.model.environment.Obstacle;
import com.github.leertaken.leertaak4.opdracht6.model.environment.Position;
import com.github.leertaken.leertaak4.opdracht6.model.robot.MobileRobot;

import java.awt.*;
import java.awt.geom.Point2D;

public class Laser extends Sensor {
	public Laser(String name, MobileRobot robot, Position localPos, Environment environment) {
		super(name, robot, localPos, environment);
		backgroundColor = Color.cyan;
	}

	protected double read(boolean first) {
		Point2D centre = new Point2D.Double(localPosition.getX(), localPosition.getY());
		Point2D front = new Point2D.Double(localPosition.getX() + range * Math.cos(localPosition.getT()),
				localPosition.getY() + range * Math.sin(localPosition.getT()));
		// reads the robot's position
		robot.readPosition(robotPosition);
		// center's coordinates according to the robot position
		robotPosition.rotateAroundAxis(centre);
		// front's coordinates according to the robot position
		robotPosition.rotateAroundAxis(front);

		double minDistance = -1.0;
		for (int i = 0; i < environment.getObstacles().size(); i++) {
			// This is really dirty: the laser uses direct access to environment's obstacles
			Obstacle obstacle = environment.getObstacles().get(i);
			if (!obstacle.getOpaque()) {
				double dist = pointToObstacle(obstacle.getPolygon(), centre, front, first);
				if (minDistance == -1.0 || (dist > 0 && dist < minDistance)) {
					minDistance = dist;
					if (minDistance > -1 && first) {
						return minDistance;
					}
				}
			}
		}
		if (minDistance > 0) {
			return minDistance;
		}
		return -1.0;
	}
}