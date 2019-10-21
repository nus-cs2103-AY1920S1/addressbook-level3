package seedu.address.commons.core;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

import seedu.address.model.aesthetics.Colour;

/**
 * A Serializable class that contains the GUI settings. Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final String DEFAULT_FONT_COLOUR = "white";

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private String fontColour;

    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        fontColour = DEFAULT_FONT_COLOUR;
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition,
                       Colour fontColour) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.fontColour = fontColour.toString();
        windowCoordinates = new Point(xPosition, yPosition);
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

    public String getFontColour() {
        return fontColour;
    }

    public void setFontColour(Colour fontColour) {
        this.fontColour = fontColour.toString();
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
