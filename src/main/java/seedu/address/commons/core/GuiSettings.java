package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 700;
    private static final double DEFAULT_WIDTH = 1000;
    private static final String DEFAULT_STYLESHEET = "view/DarkTheme.css";

    private final double windowWidth;
    private final double windowHeight;
    private String styleSheet;
    private final Point windowCoordinates;

    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        styleSheet = DEFAULT_STYLESHEET;
        windowCoordinates = null; // null represent no coordinates
    }

    public GuiSettings(double windowWidth, double windowHeight,
                       int xPosition, int yPosition,
                       String styleSheet) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.styleSheet = styleSheet;
    }

    public GuiSettings(double windowWidth, double windowHeight,
                       int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.styleSheet = DEFAULT_STYLESHEET;
    }

    public String getStyleSheets() {
        return styleSheet;
    }

    public void setStyleSheet(String styleSheet) {
        this.styleSheet = styleSheet;
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
                && Objects.equals(windowCoordinates, o.windowCoordinates)
                && styleSheet.equals(o.getStyleSheets());
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
        sb.append("Position : " + windowCoordinates + "\n");
        sb.append("Stylesheet: " + styleSheet);
        return sb.toString();
    }



}
