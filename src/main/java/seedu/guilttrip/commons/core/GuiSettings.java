package seedu.guilttrip.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import seedu.guilttrip.ui.util.FontName;
import seedu.guilttrip.ui.util.Theme;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 800;
    private static final double DEFAULT_WIDTH = 1500;
    private static final FontName DEFAULT_FONT = FontName.SEGOE_UI;
    private static final Theme DEFAULT_THEME = Theme.DARK;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private final FontName font;
    private final Theme theme;

    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        font = DEFAULT_FONT;
        theme = DEFAULT_THEME;
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition, FontName font,
                       Theme theme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.font = font;
        this.theme = theme;
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

    public FontName getFont() {
        return font;
    }

    public Theme getTheme() {
        return theme;
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

        assert font != null;
        return windowWidth == o.windowWidth
                && windowHeight == o.windowHeight
                && Objects.equals(windowCoordinates, o.windowCoordinates)
                && font.equals(o.font)
                && theme.equals(o.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, font, theme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates + "\n");
        sb.append("Font: " + font);
        sb.append("Theme: " + theme);
        return sb.toString();
    }
}
