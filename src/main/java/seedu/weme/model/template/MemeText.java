package seedu.weme.model.template;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Color;
import java.util.Set;

/**
 * Represents a piece of text on a meme.
 */
public class MemeText {

    private final String text;
    private final Coordinates coordinates;
    private final MemeTextColor color;
    private final MemeTextStyle style;
    private final MemeTextSize size;

    public MemeText(String value, Coordinates coordinates,
                    MemeTextColor color, Set<MemeTextStyle> styles, MemeTextSize size) {
        requireAllNonNull(value, coordinates, color, styles, size);
        this.text = value;
        this.coordinates = coordinates;
        this.color = color;
        this.style = MemeTextStyle.combine(styles);
        this.size = size;
    }

    public MemeText(String value, Coordinates coordinates,
                    MemeTextColor color, MemeTextStyle style, MemeTextSize size) {
        this(value, coordinates, color, Set.of(style), size);
    }

    public String getText() {
        return text;
    }

    public float getX() {
        return coordinates.getX();
    }

    public float getY() {
        return coordinates.getY();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color.getColor();
    }

    public MemeTextColor getMemeTextColor() {
        return color;
    }

    public int getStyle() {
        return style.getStyle();
    }

    public MemeTextStyle getMemeTextStyle() {
        return style;
    }

    public int getSize(int imageHeight) {
        return size.getSize(imageHeight);
    }

    public MemeTextSize getMemeTextSize() {
        return size;
    }

    @Override
    public String toString() {
        return text + " " + coordinates.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof MemeText)) {
            return false;
        }

        MemeText otherMemeText = (MemeText) other;
        return text.equals(otherMemeText.text)
            && coordinates.equals(otherMemeText.coordinates)
            && color.equals(otherMemeText.color)
            && style.equals(otherMemeText.style)
            && size.equals(otherMemeText.size);
    }
}
