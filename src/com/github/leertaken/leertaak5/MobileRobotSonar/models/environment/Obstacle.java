package com.github.leertaken.leertaak5.MobileRobotSonar.models.environment;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

public class Obstacle {

    private static final Color STANDARD_FOREGROUND_COLOR = Color.ORANGE;
    private static final Color STANDARD_BACKGROUND_COLOR = Color.BLUE;
    private static final Color STANDARD_OPAQUE_FOREGROUND_COLOR = Color.DARK_GRAY;
    private static final Color STANDARD_OPAQUE_BACKGROUND_COLOR = Color.MAGENTA;
    protected final Polygon polygon;
    protected boolean opaque;
    private String name;
    private Color foregroundColor;
    private Color backgroundColor;
    private Color opaqueBackgroundColor;
    private Color opaqueForegroundColor;

    public Obstacle() {
        this.name = "Obstacle";

        this.polygon = new Polygon();
        this.opaque = false;

        this.foregroundColor = Obstacle.STANDARD_FOREGROUND_COLOR;
        this.backgroundColor = Obstacle.STANDARD_BACKGROUND_COLOR;

        this.opaqueBackgroundColor = this.STANDARD_OPAQUE_BACKGROUND_COLOR;
        this.opaqueForegroundColor = Obstacle.STANDARD_OPAQUE_FOREGROUND_COLOR;
    }

    public Obstacle(Color foregroundColor, Color backgroundColor, Color opaqueBackgroundColor, Color opaqueForegroundColor) {
        this();
        this.foregroundColor = foregroundColor;
        this.backgroundColor = backgroundColor;

        this.opaqueBackgroundColor = opaqueBackgroundColor;
        this.opaqueForegroundColor = opaqueForegroundColor;
    }

    public boolean parseXML(String line, BufferedReader lineReader) {
        name = parseAttribute(line, "NAME");
        opaque = Boolean.valueOf(parseAttribute(line, "OPAQUE"));

        String docLine;

        try {
            while ((docLine = lineReader.readLine()) != null) {
                if (docLine.contains("</OBSTACLE")) {
                    return true;
                } else if (docLine.contains("<POINT")) {
                    int px = Integer.parseInt(parseAttribute(docLine, "X"));
                    int py = Integer.parseInt(parseAttribute(docLine, "Y"));
                    polygon.addPoint(px, py);
                } else {

                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return false;
    }

    public String toString() {
        String xml = "  <OBSTACLE NAME=" + '"' + name + '"' + " OPAQUE=" + '"' + opaque + '"' + ">\n";

        for (int j = 0; j < polygon.npoints; j++) {
            xml += "    <POINT X=" + '"' + polygon.xpoints[j] + '"' + " Y=" + '"' + polygon.ypoints[j] + '"' + "/>\n";
        }

        xml += "  </OBSTACLE>";

        return xml;
    }

    public Polygon getPolygon() {
        return this.polygon;
    }

    public boolean getOpaque() {
        return this.opaque;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getOpaqueBackgroundColor() {
        return opaqueBackgroundColor;
    }

    public void setOpaqueBackgroundColor(Color newcolor) {
        this.opaqueBackgroundColor = newcolor;
    }

    public Color getOpaqueForegroundColor() {
        return opaqueForegroundColor;
    }

    private String parseAttribute(String line, String attribute) {
        int indexInit = line.indexOf(attribute + "=");
        String parameter = line.substring(indexInit + attribute.length() + 2);
        int indexEnd = parameter.indexOf('"');

        return parameter.substring(0, indexEnd);
    }

}
