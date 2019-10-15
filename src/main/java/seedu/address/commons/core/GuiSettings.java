package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 700.0;
    private static final double DEFAULT_WIDTH = 512.0;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;

    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
    }

    /**
     * Returns a new {@code GuiSettings} with the dimensions of the app window reset to their
     * default values, and the coordinates of the input {@code GuiSettings}.
     *
     * @param settings An instance of {@code GuiSettings} to reset.
     */
    public static GuiSettings resetWindow(GuiSettings settings) {
        if (settings.getWindowCoordinates() == null) {
            return new GuiSettings();
        }

        Point coordinates = settings.getWindowCoordinates();
        int x = (int) Math.floor(coordinates.getX() + 0.5);
        int y = (int) Math.floor(coordinates.getY() + 0.5);
        return new GuiSettings(DEFAULT_WIDTH, DEFAULT_HEIGHT, x, y);
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return windowWidth == o.windowWidth
                && windowHeight == o.windowHeight
                && Objects.equals(windowCoordinates, o.windowCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates);
        return sb.toString();
    }
}
