package io.xpire.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;

    public GuiSettings() {
        this.windowWidth = DEFAULT_WIDTH;
        this.windowHeight = DEFAULT_HEIGHT;
        this.windowCoordinates = null; // null represent no coordinates
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowCoordinates = new Point(xPosition, yPosition);
    }

    public double getWindowWidth() {
        return this.windowWidth;
    }

    public double getWindowHeight() {
        return this.windowHeight;
    }

    public Point getWindowCoordinates() {
        return this.windowCoordinates != null ? new Point(this.windowCoordinates) : null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof GuiSettings)) {
            return false;
        } else {
            GuiSettings other = (GuiSettings) obj;
            return this.windowWidth == other.windowWidth
                    && this.windowHeight == other.windowHeight
                    && Objects.equals(this.windowCoordinates, other.windowCoordinates);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.windowWidth, this.windowHeight, this.windowCoordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + this.windowWidth + "\n");
        sb.append("Height : " + this.windowHeight + "\n");
        sb.append("Position : " + this.windowCoordinates);
        return sb.toString();
    }
}
