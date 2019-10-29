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

    public MemeText(String value, Coordinates coordinates, MemeTextColor color, Set<MemeTextStyle> styles) {
        requireAllNonNull(value, coordinates, color, styles);
        this.text = value;
        this.coordinates = coordinates;
        this.color = color;
        this.style = MemeTextStyle.combine(styles);
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

    public Color getColor() {
        return color.getColor();
    }

    public int getStyle() {
        return style.getStyle();
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
            && color.equals(otherMemeText.color);
    }
}
