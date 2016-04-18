package com.github.leertaken.leertaak4.opdracht6.model.device;

import com.github.leertaken.leertaak4.opdracht6.model.environment.Environment;
import com.github.leertaken.leertaak4.opdracht6.model.environment.Obstacle;
import com.github.leertaken.leertaak4.opdracht6.model.environment.Position;
import com.github.leertaken.leertaak4.opdracht6.model.robot.MobileRobot;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Sergen on 18-4-2016.
 */
public class Sonar extends Sensor {
    public Sonar(String name, MobileRobot robot, Position localPos, Environment environment) {
        super(name, robot, localPos, environment);
        backgroundColor = Color.green;
        foregroundColor = Color.black;
    }

    protected double read(boolean first) {
        Point2D centre = new Point2D.Double(localPosition.getX(), localPosition.getY());
        Point2D front = new Point2D.Double(localPosition.getX() + range * Math.cos(localPosition.getT()), localPosition.getY() + range * Math.sin(localPosition.getT()));
        robot.readPosition(robotPosition);
        robotPosition.rotateAroundAxis(centre);
        robotPosition.rotateAroundAxis(front);

        double minDistance = -1.0;
        for (int i = 0; i < environment.getObstacles().size(); i++) {
            Obstacle obstacle = environment.getObstacles().get(i);
            if (obstacle.getOpaque()) {
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
