package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    public static final String LIBERRY_THEME_CSS_PATH = "/view/LiBerryTheme.css";
    public static final String DARK_THEME_CSS_PATH = "/view/DarkTheme.css";
    public static final String COLOR_LIBERRY_THEME_LOAN_LABEL = "#CBCBCB";
    public static final String COLOR_LIBERRY_THEME_MODE_LABEL = "#979EAB";
    public static final String COLOR_LIBERRY_THEME_ALERT_1 = "#E498A1";
    public static final String COLOR_DARK_THEME_LOAN_LABEL = "#3C3C3C";
    public static final String COLOR_DARK_THEME_MODE_LABEL = "#414142";
    public static final String COLOR_DARK_THEME_ALERT_1 = "#4C3134";
    public static final String COLOR_TRANSPARENT = "transparent";

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;

    private boolean isDarkTheme;
    private boolean isDefault;

    /**
     * Default constructor for GuiSettings.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        isDefault = true;
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition, boolean isDarkTheme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        isDefault = false;
        this.isDarkTheme = isDarkTheme;
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition) {
        this(windowWidth, windowHeight, xPosition, yPosition, false);
    }

    public double getWindowWidth() {
        assert !isDefault : "Should return default width from fxml file";
        return windowWidth;
    }

    public double getWindowHeight() {
        assert !isDefault : "Should return default height from fxml file";
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    public boolean isDefault() {
        return isDefault;
    }

    /**
     * toggles between light and dark themes
     */
    public void toggleTheme() {
        if (isDarkTheme) {
            isDarkTheme = false;
        } else {
            isDarkTheme = true;
        }
    }

    public boolean isDarkTheme() {
        return isDarkTheme;
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
                && isDarkTheme == o.isDarkTheme
                && Objects.equals(windowCoordinates, o.windowCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, isDarkTheme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates + "\n");
        sb.append("Theme: " + (isDarkTheme ? "Dark" : "Light"));
        return sb.toString();
    }
}
