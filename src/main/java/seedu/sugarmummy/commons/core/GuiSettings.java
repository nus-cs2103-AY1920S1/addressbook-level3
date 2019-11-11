package seedu.sugarmummy.commons.core;

import static seedu.sugarmummy.model.aesthetics.AestheticFieldName.LABEL_BACKGROUND;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;

/**
 * A Serializable class that contains the GUI settings. Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final String DEFAULT_FONT_COLOUR = "#FFFF3A";
    private static final String DEFAULT_BACKGROUND = "#000A34";
    private static final String DEFAULT_BACKGROUND_SIZE = "auto";
    private static final String DEFAULT_BACKGROUND_REPEAT = "repeat";

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private String fontColour;
    private String background;
    private String backgroundSize;
    private String backgroundRepeat;
    private boolean showDefaultBackground = false;

    private Map<String, String> fieldsContainingInvalidReferences = new LinkedHashMap<>();

    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        setDefaultAesthetics();
        showDefaultBackground = true;
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        setDefaultAesthetics();
        windowCoordinates = new Point(xPosition, yPosition);
        showDefaultBackground = true;
    }

    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition,
                       Colour fontColour, Background background) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.fontColour = fontColour.toString();
        this.background = background.toString().equals("") ? DEFAULT_BACKGROUND : background.toString();
        this.backgroundSize = background.getBgSize();
        this.backgroundRepeat = background.getBgRepeat();
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
        if (!Colour.isValidColour(fontColour)) {
            setDefaultAesthetics();
        }
        return fontColour;
    }

    public void setFontColour(Colour fontColour) {
        this.fontColour = fontColour.toString();
    }

    public Background getBackground() {
        if (background == null
                || background.isEmpty()) {
            setDefaultAesthetics();
            showDefaultBackground = true;
        } else if (!Colour.isValidColour(background)) {
            try {
                Image image = ImageIO.read(new File(background));
                if (image == null) {
                    throw new IOException();
                }
            } catch (IOException e) {
                fieldsContainingInvalidReferences.put(LABEL_BACKGROUND, background);
                setDefaultAesthetics();
                showDefaultBackground = true;
            }
        }

        if (!Background.isValidBackgroundSize(backgroundSize)
                || !Background.isValidBackgroundRepeat(backgroundRepeat)) {
            setDefaultAesthetics();
            showDefaultBackground = true;
        }

        Background background = new Background(this.background);
        background.setShowDefaultBackground(showDefaultBackground);

        if (!background.isBackgroundColour()) {
            background.setBgSize(backgroundSize.equals("") ? DEFAULT_BACKGROUND_SIZE : backgroundSize);
            background.setBgRepeat(backgroundRepeat.equals("") ? DEFAULT_BACKGROUND_REPEAT : backgroundRepeat);
        } else {
            background.setBgSize("");
            background.setBgRepeat("");
        }

        background.setDominantColour();

        return background;
    }

    public void setBackground(Background background) {
        this.background = background.toString();
        this.backgroundSize = background.getBgSize();
        this.backgroundRepeat = background.getBgRepeat();
    }

    public void setDefaultAesthetics() {
        background = DEFAULT_BACKGROUND;
        fontColour = DEFAULT_FONT_COLOUR;
        backgroundSize = "";
        backgroundRepeat = "";
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

    /**
     * Return a map of fields in the json file that contain invalid references.
     *
     * @return Map of fields in the json file containing invalid references.
     */
    public Map<String, String> getFieldsContainingInvalidReferences() {
        return fieldsContainingInvalidReferences;
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
