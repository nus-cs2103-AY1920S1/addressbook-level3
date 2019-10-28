package seedu.weme.model.template;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a piece of text on a meme.
 */
public class MemeText {

    private final String text;
    private final Coordinates coordinates;

    public MemeText(String value, Coordinates coordinates) {
        requireAllNonNull(value, coordinates);
        this.text = value;
        this.coordinates = coordinates;
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
            && coordinates.equals(otherMemeText.coordinates);
    }
}
