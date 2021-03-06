package com.github.leertaken.leertaak5.MobileRobot.models.device;

import com.github.leertaken.leertaak5.MobileRobot.models.environment.Environment;
import com.github.leertaken.leertaak5.MobileRobot.models.environment.Position;
import com.github.leertaken.leertaak5.MobileRobot.models.robot.MobileRobot;

import java.awt.event.ActionEvent;

public class Platform extends Device {

    private static final int CLOCKWISE_ORIENTATION = 1;
    private static final int ANTICLOCKWISE_ORIENTATION = -1;
    private static final double ONE_DEGREE = 1.0;
    private final double rotationStep;
    private final double moveStep;
    private int orientation;
    private double numRotationSteps;
    private double numMoveSteps;

    public Platform(String name, MobileRobot robot, Environment environment) {
        super(name, robot, new Position(0.0, 0.0, 0.0), environment);

        this.orientation = CLOCKWISE_ORIENTATION;
        this.rotationStep = ONE_DEGREE;
        this.moveStep = ONE_DEGREE;
        this.numRotationSteps = 0;
        this.numMoveSteps = 0;

        this.addPoint(20, 20);
        this.addPoint(30, 10);
        this.addPoint(30, -10);
        this.addPoint(20, -20);
        this.addPoint(-20, -20);
        this.addPoint(-20, 20);
    }

    public void executeCommand(String command) {
        if (command.contains("ROTATERIGHT")) {
            double angle = Math.abs(Double.parseDouble(command.trim().substring(12).trim()));
            numRotationSteps = angle / rotationStep;
            orientation = CLOCKWISE_ORIENTATION;
            this.executingCommand = true;
        } else if (command.contains("ROTATELEFT")) {
            double angle = Math.abs(Double.parseDouble(command.trim().substring(11).trim()));
            numRotationSteps = angle / rotationStep;
            orientation = ANTICLOCKWISE_ORIENTATION;
            this.executingCommand = true;
        } else if (command.contains("MOVEFW")) {
            double distance = Math.abs(Double.parseDouble(command.trim().substring(7).trim()));
            numMoveSteps = distance / moveStep;
            orientation = CLOCKWISE_ORIENTATION;
            this.executingCommand = true;
        } else if (command.contains("MOVEBW")) {
            double distance = Math.abs(Double.parseDouble(command.trim().substring(7).trim()));
            numMoveSteps = distance / moveStep;
            orientation = ANTICLOCKWISE_ORIENTATION;
            this.executingCommand = true;
        } else {
            writeOut("DECLINED");
        }
    }

    public void nextStep() {
        if (!this.executingCommand) {
            return;
        }

        if (numRotationSteps > 0.0) {
            if (numRotationSteps < 1.0) {
                robotPosition.rotateAroundAxis(0.0, 0.0, numRotationSteps * orientation * rotationStep);
            } else {
                robotPosition.rotateAroundAxis(0.0, 0.0, orientation * rotationStep);
            }

            robot.writePosition(robotPosition);
            environment.processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            numRotationSteps -= 1.0;
        } else if (numMoveSteps > 0.0) {
            if (numMoveSteps < 1.0) {
                robotPosition.rotateAroundAxis(numMoveSteps * moveStep * orientation, 0.0, 0.0);
            } else {
                robotPosition.rotateAroundAxis(moveStep * orientation, 0.0, 0.0);
            }

            robot.writePosition(robotPosition);
            environment.processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            numMoveSteps -= 1.0;
        } else {
            this.executingCommand = false;
            writeOut("PLATFORM ARRIVED");
        }
    }

}
